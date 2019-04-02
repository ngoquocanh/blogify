package com.keybds.springblog.constants;

import com.keybds.springblog.controller.PostController;
import com.keybds.springblog.controller.admin.AdminPostController;
import com.keybds.springblog.controller.admin.AdminAccountController;

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
    public static final String ADMIN_ACCOUNT_UPDATE_USERNAME  = "/admin/accounts/update/username";
    public static final String ADMIN_ACCOUNT_UPDATE_EMAIL     = "/admin/accounts/update/email";
    public static final String ADMIN_ACCOUNT_UPDATE_SECURITY  = "/admin/accounts/update/security";

    /** admin posts **/
    public static final String ADMIN_POSTS_LIST            = "/admin/posts";
    public static final String ADMIN_POSTS_LIST_BASE_URL   = "/admin/posts?page=";
    public static final String ADMIN_POST_UPDATE_ID        = "/admin/posts/update/{" + AdminPostController.MODEL_ATTRIBUTE_POST_ID + "}";
    public static final String ADMIN_POST_UPDATE           = "/admin/posts/update";
    public static final String ADMIN_POST_ADD              = "/admin/posts/add";
    public static final String ADMIN_POST_DELETE_ID        = "/admin/posts/delete/{" + AdminPostController.MODEL_ATTRIBUTE_POST_ID + "}";
    public static final String ADMIN_POST_DELETE           = "/admin/posts/delete";

    /** admin ajax posts **/
    public static final String ADMIN_AJAX_POST_UPDATE      = "/admin/ajax/posts/update";

    /** public posts **/
    public static final String PUBLIC_ARTICLES_LIST        = "/articles";
    public static final String PUBLIC_POSTS_LIST_BASE_URL  = "/articles/p/";
    public static final String PUBLIC_POSTS_LIST_PAGE      = "/articles/p/{" + PostController.MODEL_ATTRIBUTE_PAGE_INDEX + "}";
    public static final String PUBLIC_ARTICLE              = "/article"; // is used in SecurityConfig
    public static final String PUBLIC_ARTICLE_LINK         = "/article/{" + PostController.MODEL_ATTRIBUTE_ARTICLE_LINK + "}";
}
