DROP DATABASE IF EXISTS blogify;
CREATE DATABASE blogify;
USE blogify;

-- phpMyAdmin SQL Dump
-- version 4.8.5
-- https://www.phpmyadmin.net/
--
-- Host: localhost:3306
-- Generation Time: May 16, 2019 at 03:03 PM
-- Server version: 10.1.38-MariaDB-0ubuntu0.18.04.1
-- PHP Version: 7.2.17-0ubuntu0.18.04.1

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET AUTOCOMMIT = 0;
START TRANSACTION;
SET time_zone = "+07:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `blogify`
--

-- --------------------------------------------------------

--
-- Table structure for table `accounts`
--

CREATE TABLE `accounts` (
    `id` bigint(20) NOT NULL,
    `username` varchar(50) NOT NULL,
    `email` varchar(150) NOT NULL,
    `first_name` varchar(40) NOT NULL,
    `last_name` varchar(40) NOT NULL,
    `password` varchar(255) NOT NULL,
    `enabled` tinyint(1) NOT NULL DEFAULT '1'
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Table structure for table `accounts_authorities`
--

CREATE TABLE `accounts_authorities` (
    `account_id` bigint(20) NOT NULL,
    `authority_id` tinyint(1) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Table structure for table `articles`
--

CREATE TABLE `articles` (
    `id` bigint(20) NOT NULL,
    `account_id` bigint(20) NOT NULL,
    `article_title` varchar(255) NOT NULL,
    `article_name` varchar(255) NOT NULL,
    `article_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
    `article_modified` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
    `article_type` tinyint(1) NOT NULL,
    `article_excerpt` text NOT NULL,
    `article_content` text NOT NULL,
    `article_image` varchar(255) DEFAULT NULL,
    `article_status` tinyint(1) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Table structure for table `articles_categories`
--

CREATE TABLE `articles_categories` (
    `id` bigint(20) NOT NULL,
    `article_id` bigint(20) NOT NULL,
    `category_id` bigint(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Table structure for table `articles_tags`
--

CREATE TABLE `articles_tags` (
    `id` bigint(20) NOT NULL,
    `tag_id` bigint(20) NOT NULL,
    `article_id` bigint(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Table structure for table `authorities`
--

CREATE TABLE `authorities` (
   `id` tinyint(1) NOT NULL,
   `authority` varchar(15) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Table structure for table `categories`
--

CREATE TABLE `categories` (
  `id` bigint(20) NOT NULL,
  `value` varchar(40) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Table structure for table `password_reset_token`
--

CREATE TABLE `password_reset_token` (
  `id` bigint(20) NOT NULL,
  `expiry_date` datetime DEFAULT NULL,
  `token` varchar(255) DEFAULT NULL,
  `account_id` bigint(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Table structure for table `tags`
--

CREATE TABLE `tags` (
    `id` bigint(20) NOT NULL,
    `value` varchar(40) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Indexes for dumped tables
--

--
-- Indexes for table `accounts`
--
ALTER TABLE `accounts`
    ADD PRIMARY KEY (`id`);

--
-- Indexes for table `accounts_authorities`
--
ALTER TABLE `accounts_authorities`
    ADD PRIMARY KEY (`account_id`,`authority_id`),
    ADD KEY `fk_accounts_has_authorities_authorities1_idx` (`authority_id`),
    ADD KEY `fk_accounts_has_authorities_accounts1_idx` (`account_id`);

--
-- Indexes for table `articles`
--
ALTER TABLE `articles`
    ADD PRIMARY KEY (`id`);

--
-- Indexes for table `articles_categories`
--
ALTER TABLE `articles_categories`
    ADD PRIMARY KEY (`id`,`article_id`,`category_id`),
    ADD KEY `fk_articles_has_categories_categories1_idx` (`category_id`),
    ADD KEY `fk_articles_has_categories_articles1_idx` (`article_id`);

--
-- Indexes for table `articles_tags`
--
ALTER TABLE `articles_tags`
    ADD PRIMARY KEY (`id`,`tag_id`,`article_id`),
    ADD KEY `fk_articles_has_tags_tags1_idx` (`tag_id`),
    ADD KEY `fk_articles_has_tags_articles1_idx` (`article_id`);

--
-- Indexes for table `authorities`
--
ALTER TABLE `authorities`
    ADD PRIMARY KEY (`id`);

--
-- Indexes for table `categories`
--
ALTER TABLE `categories`
    ADD PRIMARY KEY (`id`);

--
-- Indexes for table `password_reset_token`
--
ALTER TABLE `password_reset_token`
    ADD PRIMARY KEY (`id`),
    ADD KEY `FK76a4o6jaqpn5s2gji7q8aaxvg` (`account_id`);

--
-- Indexes for table `tags`
--
ALTER TABLE `tags`
    ADD PRIMARY KEY (`id`);

--
-- AUTO_INCREMENT for table `categories`
--
ALTER TABLE `categories`
    MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `tags`
--
ALTER TABLE `tags`
    MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `accounts`
--
ALTER TABLE `accounts`
    MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `articles`
--
ALTER TABLE `articles`
    MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `articles_categories`
--
ALTER TABLE `articles_categories`
    MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `articles_tags`
--
ALTER TABLE `articles_tags`
    MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT;

--
-- Constraints for dumped tables
--

--
-- AUTO_INCREMENT for table `password_reset_token`
--
ALTER TABLE `password_reset_token`
    MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT;

--
-- Constraints for table `accounts_authorities`
--
ALTER TABLE `accounts_authorities`
    ADD CONSTRAINT `fk_accounts_has_authorities_accounts1` FOREIGN KEY (`account_id`) REFERENCES `accounts` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
    ADD CONSTRAINT `fk_accounts_has_authorities_authorities1` FOREIGN KEY (`authority_id`) REFERENCES `authorities` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION;

--
-- Constraints for table `articles_categories`
--
ALTER TABLE `articles_categories`
    ADD CONSTRAINT `fk_articles_has_categories_articles1` FOREIGN KEY (`article_id`) REFERENCES `articles` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
    ADD CONSTRAINT `fk_articles_has_categories_categories1` FOREIGN KEY (`category_id`) REFERENCES `categories` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION;

--
-- Constraints for table `articles_tags`
--
ALTER TABLE `articles_tags`
    ADD CONSTRAINT `fk_articles_has_tags_articles1` FOREIGN KEY (`article_id`) REFERENCES `articles` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
    ADD CONSTRAINT `fk_articles_has_tags_tags1` FOREIGN KEY (`tag_id`) REFERENCES `tags` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;