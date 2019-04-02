DROP DATABASE IF EXISTS keybds;
CREATE DATABASE keybds;
USE keybds;

-- phpMyAdmin SQL Dump
-- version 4.5.4.1deb2ubuntu2.1
-- http://www.phpmyadmin.net
--
-- Host: localhost
-- Generation Time: Mar 20, 2019 at 05:18 PM
-- Server version: 5.7.25-0ubuntu0.16.04.2
-- PHP Version: 7.0.33-1+ubuntu16.04.1+deb.sury.org+1

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `keyio`
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
                          `article_title` varchar(200) NOT NULL,
                          `article_name` varchar(200) NOT NULL,
                          `article_link` varchar(255) DEFAULT NULL,
                          `article_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
                          `article_modified` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
                          `article_type_key` tinyint(1) NOT NULL,
                          `article_excerpt` text NOT NULL,
                          `article_content` text NOT NULL,
                          `article_image` varchar(200) DEFAULT NULL,
                          `article_status_key` tinyint(1) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Table structure for table `article_status`
--

CREATE TABLE `article_status` (
                                `status_key` tinyint(1) NOT NULL,
                                `status_value` varchar(10) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Table structure for table `article_type`
--

CREATE TABLE `article_type` (
                              `type_key` tinyint(1) NOT NULL,
                              `type_value` varchar(10) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Table structure for table `authorities`
--

CREATE TABLE `authorities` (
                             `id` tinyint(1) NOT NULL,
                             `authority` varchar(15) NOT NULL
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
  ADD PRIMARY KEY (`id`),
  ADD KEY `fk_articles_article_status_idx` (`article_status_key`),
  ADD KEY `fk_articles_article_type_idx` (`article_type_key`);

--
-- Indexes for table `article_status`
--
ALTER TABLE `article_status`
  ADD PRIMARY KEY (`status_key`);

--
-- Indexes for table `article_type`
--
ALTER TABLE `article_type`
  ADD PRIMARY KEY (`type_key`);

--
-- Indexes for table `authorities`
--
ALTER TABLE `authorities`
  ADD PRIMARY KEY (`id`);

--
-- AUTO_INCREMENT for dumped tables
--

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
-- Constraints for dumped tables
--

--
-- Constraints for table `accounts_authorities`
--
ALTER TABLE `accounts_authorities`
  ADD CONSTRAINT `fk_accounts_has_authorities_accounts1` FOREIGN KEY (`account_id`) REFERENCES `accounts` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  ADD CONSTRAINT `fk_accounts_has_authorities_authorities1` FOREIGN KEY (`authority_id`) REFERENCES `authorities` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION;

--
-- Constraints for table `articles`
--
ALTER TABLE `articles`
  ADD CONSTRAINT `fk_articles_article_status` FOREIGN KEY (`article_status_key`) REFERENCES `article_status` (`status_key`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  ADD CONSTRAINT `fk_articles_article_type` FOREIGN KEY (`article_type_key`) REFERENCES `article_type` (`type_key`) ON DELETE NO ACTION ON UPDATE NO ACTION;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;