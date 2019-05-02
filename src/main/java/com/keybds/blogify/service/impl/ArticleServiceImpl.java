package com.keybds.blogify.service.impl;

import com.github.slugify.Slugify;
import com.keybds.blogify.controller.admin.AdminPostController;
import com.keybds.blogify.enums.ArticleEnum;
import com.keybds.blogify.enums.StatusMessageCode;
import com.keybds.blogify.exceptions.MvcException;
import com.keybds.blogify.model.ArticleStatus;
import com.keybds.blogify.model.ArticleType;
import com.keybds.blogify.repository.ArticleRepository;
import com.keybds.blogify.service.ArticleService;
import com.keybds.blogify.model.Article;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.Optional;
import java.util.Random;

@Service
@Transactional
public class ArticleServiceImpl extends AbstractService implements ArticleService {

    @Autowired
    private ArticleRepository articleRepository;

    private static final String ZONE_VIETNAM_HCM    = "Asia/Ho_Chi_Minh";

    @Transactional(readOnly = true)
    @Override
    public List<Article> getAllPublishedPosts() {
        return articleRepository.findByArticleStatusEquals(new ArticleStatus(ArticleEnum.STATUS_PUBLIC));
    }

    @Transactional(readOnly = true)
    @Override
    public Page<Article> getAllPublishedPosts(Pageable pageable) {
        if (isPaged(pageable)) {
            return articleRepository.findByArticleStatusEquals(new ArticleStatus(ArticleEnum.STATUS_PUBLIC), pageable);
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

                // new data from form update post include: id, title, content
                articleToUpdate.setId(article.getId());
                articleToUpdate.setArticleTitle(article.getArticleTitle());
                articleToUpdate.setArticleExcerpt(article.getArticleExcerpt());
                articleToUpdate.setArticleContent(article.getArticleContent());
                articleToUpdate.setArticleStatus(article.getArticleStatus());

                ZoneId zone = ZoneId.of(ZONE_VIETNAM_HCM);
                ZonedDateTime zonedDateTime = ZonedDateTime.now(zone);
                articleToUpdate.setArticleModified(zonedDateTime);

                articleToUpdate.setArticleType(new ArticleType(ArticleEnum.TYPE_POST));

                // old data existed from database
                articleToUpdate.setAccountId(articleExisted.getAccountId());
                articleToUpdate.setArticleLink(articleExisted.getArticleLink());
                articleToUpdate.setArticleName(articleExisted.getArticleName());
                articleToUpdate.setArticleDate(articleExisted.getArticleDate());
                articleToUpdate.setArticleImage(articleExisted.getArticleImage());

                // execute update post
                articleUpdated = articleRepository.save(articleToUpdate);
            } else {
                throw new MvcException(StatusMessageCode.POST_NOT_FOUND);
            }
        } else {
            throw new MvcException(StatusMessageCode.POST_NOT_FOUND);
        }
        return articleUpdated;
    }

    @Override
    public Article createPost(Article article) throws MvcException {
        Article articleToCreate = new Article();

        // new data from form add post include: status, title, excerpt, content
        articleToCreate.setArticleStatus(article.getArticleStatus());
        articleToCreate.setArticleTitle(article.getArticleTitle());
        articleToCreate.setArticleExcerpt(article.getArticleExcerpt());
        articleToCreate.setArticleContent(article.getArticleContent());

        // name created from controller
        articleToCreate.setArticleName(article.getArticleName());

        // type always is POST
        articleToCreate.setArticleType(new ArticleType(ArticleEnum.TYPE_POST));

        ZoneId zone = ZoneId.of(ZONE_VIETNAM_HCM);
        ZonedDateTime zonedDateTime = ZonedDateTime.now(zone);

        // hard code part, will update later
        // no need to set article id
        articleToCreate.setAccountId(1L);
        articleToCreate.setArticleLink("test");
        articleToCreate.setArticleDate(zonedDateTime);
        articleToCreate.setArticleModified(zonedDateTime);
        articleToCreate.setArticleImage("test");

        Article articleCreated = articleRepository.save(articleToCreate);
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
}
