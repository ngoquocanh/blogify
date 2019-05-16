package com.quopri.blogify.converters;

import com.quopri.blogify.model.Article;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter
public class ArticleTypeConverter implements AttributeConverter<Article.Type, Integer> {

    @Override
    public Integer convertToDatabaseColumn(Article.Type attribute) {
        return attribute.getKey();
    }

    @Override
    public Article.Type convertToEntityAttribute(Integer dbData) {
        return Article.Type.findByKey(dbData);
    }
}