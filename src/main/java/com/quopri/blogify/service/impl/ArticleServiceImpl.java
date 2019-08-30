package com.quopri.blogify.service.impl;

import com.github.slugify.Slugify;
import com.quopri.blogify.controller.admin.AdminPostController;
import com.quopri.blogify.entity.*;
import com.quopri.blogify.enums.StatusMessageCode;
import com.quopri.blogify.exceptions.MvcException;
import com.quopri.blogify.repository.*;
import com.quopri.blogify.service.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.*;

@Service
@Transactional
public class ArticleServiceImpl extends AbstractService implements ArticleService {

    @Autowired
    private ArticleRepository articleRepository;

    @Autowired
    private TagRepository tagRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private ArticleTagRepository articleTagRepository;

    @Autowired
    private ArticleCategoryRepository articleCategoryRepository;

    private static final String ZONE_VIETNAM_HCM    = "Asia/Ho_Chi_Minh";

    @Transactional(readOnly = true)
    @Override
    public List<Article> getAllPublishedPosts() {
        return articleRepository.findAllByArticleStatus(Article.Status.PUBLISHED);
    }

    @Transactional(readOnly = true)
    @Override
    public Page<Article> getAllPublishedPosts(Pageable pageable) {
        if (isPaged(pageable)) {
            return articleRepository.findAllByArticleStatus(Article.Status.PUBLISHED, pageable);
        } else {
            return Page.empty();
        }
    }

    @Override
    public Page<Article> getAllPublishedPostsByTag(Pageable pageable, Long tagId) {
        if (isPaged(pageable)) {
            return articleRepository.findAllByTagId(pageable, tagId);
        } else {
            return Page.empty();
        }
    }

    @Transactional(readOnly = true)
    @Override
    public List<Article> getAllPosts() {
        return (List<Article>) articleRepository.findAll();
    }

    @Transactional(readOnly = true)
    @Override
    public Page<Article> getAllPosts(Pageable pageable) {
        if (isPaged(pageable)) {
            return articleRepository.findAll(pageable);
        } else {
            return Page.empty();
        }
    }

    @Override
    public Page<Article> getAllPosts(Integer pageIndex, Integer pageSize, String sortName, String sortDir) {
        Sort sortRequest = sortDir.equals(AdminPostController.MODEL_ATTRIBUTE_SORT_DIR_DESC) ? Sort.by(Sort.Direction.DESC, sortName) : Sort.by(Sort.Direction.ASC, sortName);
        Pageable pageable = PageRequest.of(pageIndex, pageSize, sortRequest);
        return getAllPosts(pageable);
    }

    @Transactional(readOnly = true)
    @Override
    public Article getPost(String postName) throws MvcException {
        Article articleExisted = articleRepository.findByArticleNameIgnoreCase(postName);
        if (articleExisted == null) {
            throw new MvcException(StatusMessageCode.POST_NOT_FOUND);
        }
        return articleExisted;
    }

    @Override
    public Article getPost(Long postId) throws MvcException {
        Article article;
        if (articleRepository.existsById(postId)) {
            Optional<Article> articleExisted = articleRepository.findById(postId);
            if (articleExisted.isPresent()) {
                article = articleExisted.get();
            } else {
                throw new MvcException(StatusMessageCode.POST_NOT_FOUND);
            }
        } else {
            throw new MvcException(StatusMessageCode.POST_NOT_FOUND);
        }
        return article;
    }

    @Override
    public Article updatePost(Article article) throws MvcException {
        Article articleUpdated;
        if (articleRepository.existsById(article.getId())) {
            Article articleExisted;
            Optional<Article> optionalArticleExisted = articleRepository.findById(article.getId());
            if (optionalArticleExisted.isPresent()) {
                articleExisted = optionalArticleExisted.get();
                Article articleToUpdate = new Article();
                articleToUpdate.setId(article.getId());
                articleToUpdate.setAccountId(articleExisted.getAccountId());
                articleToUpdate.setArticleTitle(article.getArticleTitle());
                articleToUpdate.setArticleName(articleExisted.getArticleName());
                articleToUpdate.setArticleDate(articleExisted.getArticleDate());
                ZoneId zone = ZoneId.of(ZONE_VIETNAM_HCM);
                ZonedDateTime zonedDateTime = ZonedDateTime.now(zone);
                articleToUpdate.setArticleModified(zonedDateTime);
                articleToUpdate.setArticleType(article.getArticleType());
                articleToUpdate.setArticleExcerpt(article.getArticleExcerpt());
                articleToUpdate.setArticleContent(article.getArticleContent());
                articleToUpdate.setArticleImage(article.getArticleImage());
                articleToUpdate.setArticleStatus(article.getArticleStatus());
                Set<Category> categoriesExisted = new HashSet<>();
                for (Category category : article.getCategories()) {
                    Category categoryExisted = categoryRepository.findById(category.getId()).get();
                    categoriesExisted.add(categoryExisted);
                }
                articleToUpdate.setCategories(categoriesExisted);
                articleToUpdate.setTags(new HashSet<>());
                List<Tag> tagsExisted = addTagToPost(articleToUpdate, article.getTags());
                for (Tag tag : tagsExisted) {
                    articleToUpdate.getTags().add(tag);
                }
                articleUpdated = articleRepository.save(articleToUpdate);
            } else {
                throw new MvcException(StatusMessageCode.POST_NOT_FOUND);
            }
        } else {
            throw new MvcException(StatusMessageCode.POST_NOT_FOUND);
        }
        return articleUpdated;
    }

    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public Article createPost(Article article) throws MvcException {
        Article articleToCreate = new Article();
        articleToCreate.setArticleStatus(article.getArticleStatus());
        articleToCreate.setArticleTitle(article.getArticleTitle());
        articleToCreate.setArticleExcerpt(article.getArticleExcerpt());
        articleToCreate.setArticleContent(article.getArticleContent());
        articleToCreate.setArticleImage(article.getArticleImage());
        articleToCreate.setArticleName(article.getArticleName());
        articleToCreate.setArticleType(article.getArticleType());
        ZoneId zone = ZoneId.of(ZONE_VIETNAM_HCM);
        ZonedDateTime zonedDateTime = ZonedDateTime.now(zone);
        articleToCreate.setArticleDate(zonedDateTime);
        articleToCreate.setArticleModified(zonedDateTime);
        articleToCreate.setAccountId(article.getAccountId());
        List<Tag> tagsExisted = addTagToPost(articleToCreate, article.getTags());
        Article articleCreated = articleRepository.save(articleToCreate);

        // category
        for (Category category : article.getCategories()) {
            Optional<Category> categoryOptional = categoryRepository.findById(category.getId());
            if (categoryOptional.isPresent()) {
                Category categoryExisted = categoryOptional.get();
                ArticleCategory articleCategory = new ArticleCategory();
                articleCategory.setArticleId(articleCreated.getId());
                articleCategory.setCategoryId(categoryExisted.getId());
                articleCategoryRepository.save(articleCategory);
            }
        }

        for (Tag tag : tagsExisted) {
            ArticleTag articleTag = new ArticleTag();
            articleTag.setTagId(tag.getId());
            articleTag.setArticleId(articleCreated.getId());
            articleTagRepository.save(articleTag);
        }
        return articleCreated;
    }

    @Override
    public Boolean existsByName(String articleName) throws MvcException {
        Article articleExisted = articleRepository.findByArticleNameIgnoreCase(articleName);
        return (articleExisted != null) ? true : false;
    }

    @Override
    public String createSlug(String articleTitle) throws MvcException {
        Slugify slugify;
        String slug = null;
        String result = null;
        slugify = new Slugify().withLowerCase(true);
        slug = slugify.slugify(articleTitle);
        if (existsByName(slug)) {
            // generate slug with current date
            // result form: slug-c21924
            ZoneId zone = ZoneId.of(ZONE_VIETNAM_HCM);
            ZonedDateTime zonedDateTime = ZonedDateTime.now(zone);
            String currentMonth = String.valueOf(zonedDateTime.getMonthValue()); // value from 1 to 12
            String yearStr = String.valueOf(zonedDateTime.getYear());
            String currentYear = yearStr.substring(yearStr.length() - 2);
            String currentDay = String.valueOf(zonedDateTime.getDayOfMonth()); // value from 1 to 31
            String generateWithDate = "-c".concat(currentMonth).concat(currentYear).concat(currentDay);
            result = slug.concat(generateWithDate);
            if (existsByName(result)) {
                // generate slug with random number between min and max
                // result form: slug-r219243
                int max = 999999;
                int min = 10000;
                Random random = new Random();
                int value = random.nextInt((max - min) + 1) + min;
                String generateWithNum = "-r".concat(String.valueOf(value));
                result = slug.concat(generateWithNum);
                if (existsByName(result)) {
                    throw new MvcException(StatusMessageCode.POST_EXISTED);
                }
                return result;
            } else {
                return result;
            }
        } else {
            return slug;
        }
    }

    @Override
    public void deletePost(Long postId) throws MvcException {
        if (articleRepository.existsById(postId)) {
            articleRepository.deleteById(postId);
        } else {
            throw new MvcException(StatusMessageCode.POST_NOT_FOUND);
        }
    }

    @Override
    public void deletePosts(List<Long> postIds) throws MvcException {
        for (Long postId : postIds) {
            if (articleRepository.existsById(postId)) {
                articleRepository.deleteById(postId);
            }
        }
    }

    private List<Tag> addTagToPost(Article post, Set<Tag> tags) {
        List<Tag> tagsExisted = new ArrayList<>();
        for (Tag tag : tags) {
            Tag tagExisted = tagRepository.findByValueIgnoreCase(tag.getValue());
            if (tagExisted == null) {
                post.getTags().add(tag);
            } else {
                tagsExisted.add(tagExisted);
            }
        }
        return tagsExisted;
    }
}
