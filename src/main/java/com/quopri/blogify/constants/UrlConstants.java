package com.quopri.blogify.constants;

import com.quopri.blogify.controller.stranger.PostController;
import com.quopri.blogify.controller.admin.AdminPostController;
import com.quopri.blogify.controller.admin.AdminAccountController;

public class UrlConstants {

    public static final String HOME                           = "/";
    public static final String SIGN_IN                        = "/login";
    public static final String SIGN_UP                        = "/register";
    public static final String ACCESS_DENIED                  = "/denied";

    /** admin accounts **/
    public static final String ADMIN_PREFIX                   = "/admin"; // is used in SecurityConfig
    public static final String ADMIN_ACCOUNTS_LIST            = "/admin/accounts";
    public static final String ADMIN_ACCOUNTS_LIST_BASE_URL   = "/admin/accounts?page=";
    public static final String ADMIN_ACCOUNT_ADD              = "/admin/accounts/add";
    public static final String ADMIN_ACCOUNT_UPDATE           = "/admin/accounts/update/{" + AdminAccountController.MODEL_ATTRIBUTE_ACCOUNT_ID + "}";
    public static final String ADMIN_ACCOUNT_UPDATE_INFO      = "/admin/accounts/update/info";
    public static final String ADMIN_ACCOUNT_RESET_PASSWORD_ID  = "/admin/accounts/{id}/reset-password";
    public static final String ADMIN_ACCOUNT_RESET_PASSWORD     = "/admin/accounts/reset-password";
    public static final String ADMIN_ACCOUNTS_DELETE            = "/admin/accounts/delete";

    /** admin posts **/
    public static final String ADMIN_POSTS_LIST            = "/admin/posts";
    public static final String ADMIN_POSTS_LIST_BASE_URL   = "/admin/posts?page=";
    public static final String ADMIN_POST_UPDATE_ID        = "/admin/posts/update/{" + AdminPostController.MODEL_ATTRIBUTE_POST_ID + "}";
    public static final String ADMIN_POST_UPDATE           = "/admin/posts/update";
    public static final String ADMIN_POST_ADD              = "/admin/posts/add";
    public static final String ADMIN_POST_DELETE_ID        = "/admin/posts/delete/{" + AdminPostController.MODEL_ATTRIBUTE_POST_ID + "}";
    public static final String ADMIN_POSTS_DELETE          = "/admin/posts/delete";

    /** admin tags **/
    public static final String ADMIN_TAGS_LIST            = "/admin/tags";
    public static final String ADMIN_TAGS_LIST_BASE_URL   = "/admin/tags?page=";
    public static final String ADMIN_TAG_UPDATE_ID        = "/admin/tags/update/{" + AdminPostController.MODEL_ATTRIBUTE_POST_ID + "}";
    public static final String ADMIN_TAG_UPDATE           = "/admin/tags/update";
    public static final String ADMIN_TAGS_DELETE          = "/admin/tags/delete";
    public static final String ADMIN_TAG_ADD              = "/admin/tags/add";

    /** admin categories **/
    public static final String ADMIN_CATEGORIES_LIST            = "/admin/categories";
    public static final String ADMIN_CATEGORIES_LIST_BASE_URL   = "/admin/categories?page=";
    public static final String ADMIN_CATEGORY_UPDATE_ID         = "/admin/categories/update/{" + AdminPostController.MODEL_ATTRIBUTE_POST_ID + "}";
    public static final String ADMIN_CATEGORY_UPDATE            = "/admin/categories/update";
    public static final String ADMIN_CATEGORIES_DELETE          = "/admin/categories/delete";
    public static final String ADMIN_CATEGORY_ADD               = "/admin/categories/add";

    /** admin ajax **/
    public static final String ADMIN_AJAX_POST_UPDATE      = "/admin/ajax/posts/update";

    /** public ajax **/
    public static final String PUBLIC_AJAX_PREFIX          = "/aj";
    public static final String PUBLIC_AJAX_POST_TAG_CLOUD  = "/aj/tag-cloud";

    /** public posts **/
    public static final String PUBLIC_ARTICLES_LIST        = "/articles";
    public static final String PUBLIC_POSTS_LIST_BASE_URL  = "/articles/p/";
    public static final String PUBLIC_POSTS_LIST_PAGE      = "/articles/p/{" + PostController.MODEL_ATTRIBUTE_PAGE_INDEX + "}";
    public static final String PUBLIC_ARTICLE              = "/article"; // is used in SecurityConfig
    public static final String PUBLIC_ARTICLE_LINK         = "/article/{" + PostController.MODEL_ATTRIBUTE_ARTICLE_LINK + "}";
}
