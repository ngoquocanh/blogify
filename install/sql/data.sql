DELETE FROM `articles`;
DELETE FROM `article_status`;
DELETE FROM `article_type`;
DELETE FROM `accounts_authorities`;
DELETE FROM `accounts`;
DELETE FROM `authorities`;

ALTER TABLE `articles` AUTO_INCREMENT = 1;
ALTER TABLE `accounts` AUTO_INCREMENT = 1;

-- ----------------------------
-- records of accounts
-- ----------------------------
INSERT INTO `accounts` VALUES ('1', 'admin', 'admin@gmail.com', 'Admin', 'Jones', '$2a$10$EblZqNptyYvcLm/VwDCVAuBjzZOI7khzdyGPBr08PpIi0na624b8.', '1');
INSERT INTO `accounts` VALUES ('2', 'user', 'user@gmail.com', 'User', 'Charlie', '$2a$10$EblZqNptyYvcLm/VwDCVAuBjzZOI7khzdyGPBr08PpIi0na624b8.', '1');

-- ----------------------------
-- records of authorities
-- ----------------------------
INSERT INTO `authorities` (`id`, `authority`) VALUES (1, 'ROLE_ADMIN'), (2, 'ROLE_USER');

-- ----------------------------
-- records of accounts_authorities
-- ----------------------------
INSERT INTO `accounts_authorities` (`account_id`, `authority_id`) VALUES (1, 1), (2, 2);

-- ----------------------------
-- records of article_status
-- ----------------------------
INSERT INTO `article_status` (`status_key`, `status_value`) VALUES (1, 'Public'), (2, 'Draft');

-- ----------------------------
-- records of article_type
-- ----------------------------
INSERT INTO `article_type` (`type_key`, `type_value`) VALUES (1, 'Post'), (2, 'Page');

-- ----------------------------
-- records of articles
-- ----------------------------
INSERT INTO `articles` (`id`, `account_id`, `article_title`, `article_name`, `article_link`, `article_date`, `article_modified`, `article_type_key`, `article_excerpt`, `article_content`, `article_image`, `article_status_key`) VALUES
(1, 1, 'How To Install Homebrew On Mac OS', 'how-to-install-homebrew-on-macos', 'how-to-install-homebrew-on-macos', '2019-01-23 01:44:16', '2019-01-23 01:44:16', '1', 'Homebrew is a free and open-source software package management system for Macs which makes installing lots of different software like Git, Ruby, and Node simpler. Homebrew is known as the missing package manager for macOS. It lets you avoid possible security ...', '<p>Homebrew is a free and open-source software package management system for Macs which makes installing lots of different software like Git, Ruby, and Node simpler. Homebrew is known as the missing package manager for macOS. It lets you avoid possible security problems associated with using the&nbsp;sudo command to install software like Node.</p>\r\n<img alt="image" border="0" src="https://lh4.googleusercontent.com/-3mCGXwNcf5E/XJUVrEl5LxI/AAAAAAAACls/jLfAZQbhORszSZvH27PG_M4q0VuNDhJ_gCLcBGAs/s1600/homebrew.jpg" title="image">\r\n<h2>Prerequisites</h2>\r\n<p>\r\n   For installing Homebrew on Mac OS / Mac OS X older versions, before macOS Mojave 10.14, High Sierra, Sierra 10.12, El Capitan 10.11, etc. You probably need to install Xcode or Command Line Tools on the Mac first before you can install Homebrew on that Mac OS version.\r\n   So first get command line tools by copy the below code into Terminal. The Terminal application is located in the Utilities folder in the Applications folder.\r\n</p>\r\n<script src="https://gist.github.com/ngoquocanh/3c9324f3412d26c3d9b3a7f1ca72c740.js"></script>\r\n<h2>Steps Install Homebrew</h2>\r\n<ol>\r\n   <li>Open the Terminal application.</li>\r\n   <li>Copy and paste the following command, and press Enter:</li>\r\n</ol>\r\n<script src="https://gist.github.com/ngoquocanh/d5fec7a813daa9e1831e3ad3a25f5485.js"></script>\r\n<p>\r\n   You’ll see messages in the Terminal explaining what you need to do to complete the installation process.\r\n</p>\r\n<p>\r\n   Run the following command once you’re done to ensure Homebrew is installed and working properly:\r\n</p>\r\n<script src="https://gist.github.com/ngoquocanh/3a1c6e5dcd4abb8c576920924296d9e3.js"></script>\r\n<p>\r\n   To check the current version of Homebrew type:\r\n</p>\r\n<script src="https://gist.github.com/ngoquocanh/29b8b7c60e96632048ced6309abda141.js"></script>\r\n<h2>\r\n   Update Homebrew (Optional)\r\n</h2>\r\n<p>\r\n   New versions of Homebrew come out frequently, so make sure you update it before updating any of the other software components that you’ve installed using Homebrew.\r\n</p>\r\n<script src="https://gist.github.com/ngoquocanh/17875cce0a7536c5ba9aee87e9ed6ecd.js"></script>\r\n<h2>\r\n   Uninstall Homebrew (Optional)\r\n</h2>\r\n<p>\r\n   If you have installed Homebrew but later decide you want to remove for some reason, you can uninstall it by executing the command below in a Terminal.\r\n</p>\r\n<script src="https://gist.github.com/ngoquocanh/5d10d49d072a53b2f951ac4cbce023ec.js"></script>\r\n<p>\r\n   This command downloads and runs the uninstaller script. Follow the instructions and Homebrew will be removed from your computer.\r\n</p>\r\n<p>\r\n   Enjoy Homebrew!\r\n</p>', NULL, 1);
INSERT INTO `articles` (`id`, `account_id`, `article_title`, `article_name`, `article_link`, `article_date`, `article_modified`, `article_type_key`, `article_excerpt`, `article_content`, `article_image`, `article_status_key`) VALUES
(2, 1, 'Lorem ipsum dolor sit amet, consectetur adipisicing elit. Excepturi cupiditate neque obcaecati illo in beatae.', '1000-ways-to-title-something', 'http://stackoverflow.com/questions/521171/a-java-collection-of-value-pairs-tuples', '2019-01-23 01:44:16', '2019-01-23 01:44:16', '1', 'Lorem ipsum dolor sit amet, consectetur adipisicing elit. Rem alias illo eum quae cum enim aspernatur facere architecto, aliquid, et earum, assumenda laudantium adipisci nobis laboriosam itaque! Enim earum, officiis quae, fuga deserunt doloribus voluptate.', 'Prerequisites Development environment...', NULL, 1),
(3, 1, 'Lorem ipsum dolor sit amet, consectetur adipisicing elit. Perferendis eos quod sit pariatur, nisi eum odit error!', 'variations-on-json-key-value-pairs-in-spring-mvc', 'http://stackoverflow.com/questions/521171/a-java-collection-of-value-pairs-tuples', '2019-01-23 01:44:16', '2019-01-23 01:44:16', '1', 'Lorem ipsum dolor sit amet, consectetur adipisicing elit. Libero nostrum, eaque illum cupiditate aliquam quisquam molestiae nemo vel nisi officia ratione a excepturi ea ad eius unde velit expedita ipsa.', 'Prerequisites Development environment...', NULL, 1),
(4, 1, 'Lorem ipsum dolor sit amet, consectetur adipisicing elit. Eaque doloremque pariatur itaque delectus laborum consectetur eveniet cum, iste dolor adipisci!', 'jsoup-parsing-and-traversing-document-and-url-javatips-info', 'http://stackoverflow.com/questions/521171/a-java-collection-of-value-pairs-tuples', '2019-01-23 01:44:16', '2019-01-23 01:44:16', '1', 'Lorem ipsum dolor sit amet, consectetur adipisicing elit. Ipsum similique hic at culpa perspiciatis saepe quae laborum, a eaque molestiae delectus, alias fugiat, incidunt natus cupiditate debitis sequi recusandae sint quibusdam nesciunt!', 'Prerequisites Development environment...', NULL, 1),
(5, 1, 'Lorem ipsum dolor sit amet, consectetur adipisicing elit. Dicta, hic. Maiores consequuntur repudiandae quis, veritatis ad temporibus sapiente fuga tempora sunt nam.', 'freestanding-note-1', 'http://stackoverflow.com/questions/521171/a-java-collection-of-value-pairs-tuples', '2019-01-23 01:44:16', '2019-01-23 01:44:16', '1', 'Lorem ipsum dolor sit amet, consectetur adipisicing elit. Officia deleniti earum pariatur mollitia, reprehenderit animi fuga sit in voluptate consequatur eveniet quasi, unde dignissimos. Molestias sequi libero, deserunt fugiat quasi velit ex alias?', 'Prerequisites Development environment...', NULL, 1),
(6, 1, 'Lorem ipsum dolor sit amet, consectetur adipisicing elit. Ad asperiores dolorem, saepe sint fugit illo maiores officia repudiandae!', '200-ways-to-title-something', 'http://stackoverflow.com/questions/521171/a-java-collection-of-value-pairs-tuples', '2019-01-23 01:44:16', '2019-01-23 01:44:16', '1', 'Lorem ipsum dolor sit amet, consectetur adipisicing elit. Esse eligendi laborum itaque excepturi soluta consequatur nobis, dolore aspernatur autem nulla vel odit quae illum cupiditate, ipsam architecto laboriosam voluptas sit temporibus!', 'Prerequisites Development environment...', NULL, 1),
(7, 1, 'Lorem ipsum dolor sit amet, consectetur adipisicing elit. Quaerat saepe eligendi velit laboriosam earum, necessitatibus ea soluta qui aliquam expedita? Maxime officia dicta repudiandae.', 'albert-test-first-1-title', 'http://stackoverflow.com/questions/521171/a-java-collection-of-value-pairs-tuples', '2019-01-23 01:44:16', '2019-01-23 01:44:16', '1', 'Lorem ipsum dolor sit amet, consectetur adipisicing elit. Architecto autem magnam voluptate omnis, corrupti molestias dolorem? Dolorum esse laboriosam aliquid harum, maxime laudantium ipsum, enim, sequi quibusdam alias maiores necessitatibus similique molestiae fugiat voluptatem.', 'Prerequisites Development environment...', NULL, 1),
(8, 1, 'Lorem ipsum dolor sit amet, consectetur adipisicing elit. Quo quasi animi sunt est pariatur minima praesentium, laudantium quia architecto veritatis.', 'Lorem ipsum dolor sit amet, consectetur adipisicing elit. Blanditiis perspiciatis rerum ipsam, nemo mollitia adipisci fugiat repudiandae quae praesentium soluta veritatis fuga amet dolorem nulla!', 'http://stackoverflow.com/questions/521171/a-java-collection-of-value-pairs-tuples', '2019-01-23 08:44:16', '2019-01-23 08:44:16', '1', 'Lorem ipsum dolor sit amet, consectetur adipisicing elit. Rem alias illo eum quae cum enim aspernatur facere architecto, aliquid, et earum, assumenda laudantium adipisci nobis laboriosam itaque! Enim earum, officiis quae, fuga deserunt doloribus voluptate.', 'Prerequisites Development environment...', NULL, 1),
(9, 1, 'Lorem ipsum dolor sit amet, consectetur adipisicing elit. Quo quasi animi sunt est pariatur minima praesentium, laudantium quia architecto veritatis.', 'Lorem ipsum dolor sit amet, consectetur adipisicing elit. Blanditiis perspiciatis rerum ipsam, nemo mollitia adipisci fugiat repudiandae quae praesentium soluta veritatis fuga amet dolorem nulla!', 'http://stackoverflow.com/questions/521171/a-java-collection-of-value-pairs-tuples', '2019-01-23 08:44:16', '2019-01-23 08:44:16', '1', 'Lorem ipsum dolor sit amet, consectetur adipisicing elit. Rem alias illo eum quae cum enim aspernatur facere architecto, aliquid, et earum, assumenda laudantium adipisci nobis laboriosam itaque! Enim earum, officiis quae, fuga deserunt doloribus voluptate.', 'Prerequisites Development environment...', NULL, 1),
(10, 1, 'Lorem ipsum dolor sit amet, consectetur adipisicing elit. Quo quasi animi sunt est pariatur minima praesentium, laudantium quia architecto veritatis.', 'Lorem ipsum dolor sit amet, consectetur adipisicing elit. Blanditiis perspiciatis rerum ipsam, nemo mollitia adipisci fugiat repudiandae quae praesentium soluta veritatis fuga amet dolorem nulla!', 'http://stackoverflow.com/questions/521171/a-java-collection-of-value-pairs-tuples', '2019-01-23 08:44:16', '2019-01-23 08:44:16', '1', 'Lorem ipsum dolor sit amet, consectetur adipisicing elit. Rem alias illo eum quae cum enim aspernatur facere architecto, aliquid, et earum, assumenda laudantium adipisci nobis laboriosam itaque! Enim earum, officiis quae, fuga deserunt doloribus voluptate.', 'Prerequisites Development environment...', NULL, 1),
(11, 1, 'Lorem ipsum dolor sit amet, consectetur adipisicing elit. Quo quasi animi sunt est pariatur minima praesentium, laudantium quia architecto veritatis.', 'Lorem ipsum dolor sit amet, consectetur adipisicing elit. Blanditiis perspiciatis rerum ipsam, nemo mollitia adipisci fugiat repudiandae quae praesentium soluta veritatis fuga amet dolorem nulla!', 'http://stackoverflow.com/questions/521171/a-java-collection-of-value-pairs-tuples', '2019-01-23 08:44:16', '2019-01-23 08:44:16', '1', 'Lorem ipsum dolor sit amet, consectetur adipisicing elit. Rem alias illo eum quae cum enim aspernatur facere architecto, aliquid, et earum, assumenda laudantium adipisci nobis laboriosam itaque! Enim earum, officiis quae, fuga deserunt doloribus voluptate.', 'Prerequisites Development environment...', NULL, 1),
(12, 1, 'Lorem ipsum dolor sit amet, consectetur adipisicing elit. Quo quasi animi sunt est pariatur minima praesentium, laudantium quia architecto veritatis.', 'Lorem ipsum dolor sit amet, consectetur adipisicing elit. Blanditiis perspiciatis rerum ipsam, nemo mollitia adipisci fugiat repudiandae quae praesentium soluta veritatis fuga amet dolorem nulla!', 'http://stackoverflow.com/questions/521171/a-java-collection-of-value-pairs-tuples', '2019-01-23 08:44:16', '2019-01-23 08:44:16', '1', 'Lorem ipsum dolor sit amet, consectetur adipisicing elit. Rem alias illo eum quae cum enim aspernatur facere architecto, aliquid, et earum, assumenda laudantium adipisci nobis laboriosam itaque! Enim earum, officiis quae, fuga deserunt doloribus voluptate.', 'Prerequisites Development environment...', NULL, 1),
(13, 1, 'Lorem ipsum dolor sit amet, consectetur adipisicing elit. Quo quasi animi sunt est pariatur minima praesentium, laudantium quia architecto veritatis.', 'Lorem ipsum dolor sit amet, consectetur adipisicing elit. Blanditiis perspiciatis rerum ipsam, nemo mollitia adipisci fugiat repudiandae quae praesentium soluta veritatis fuga amet dolorem nulla!', 'http://stackoverflow.com/questions/521171/a-java-collection-of-value-pairs-tuples', '2019-01-23 08:44:16', '2019-01-23 08:44:16', '1', 'Lorem ipsum dolor sit amet, consectetur adipisicing elit. Rem alias illo eum quae cum enim aspernatur facere architecto, aliquid, et earum, assumenda laudantium adipisci nobis laboriosam itaque! Enim earum, officiis quae, fuga deserunt doloribus voluptate.', 'Prerequisites Development environment...', NULL, 1),
(14, 1, 'Lorem ipsum dolor sit amet, consectetur adipisicing elit. Quo quasi animi sunt est pariatur minima praesentium, laudantium quia architecto veritatis.', 'Lorem ipsum dolor sit amet, consectetur adipisicing elit. Blanditiis perspiciatis rerum ipsam, nemo mollitia adipisci fugiat repudiandae quae praesentium soluta veritatis fuga amet dolorem nulla!', 'http://stackoverflow.com/questions/521171/a-java-collection-of-value-pairs-tuples', '2019-01-23 08:44:16', '2019-01-23 08:44:16', '1', 'Lorem ipsum dolor sit amet, consectetur adipisicing elit. Rem alias illo eum quae cum enim aspernatur facere architecto, aliquid, et earum, assumenda laudantium adipisci nobis laboriosam itaque! Enim earum, officiis quae, fuga deserunt doloribus voluptate.', 'Prerequisites Development environment...', NULL, 1),
(15, 1, 'Lorem ipsum dolor sit amet, consectetur adipisicing elit. Quo quasi animi sunt est pariatur minima praesentium, laudantium quia architecto veritatis.', 'Lorem ipsum dolor sit amet, consectetur adipisicing elit. Blanditiis perspiciatis rerum ipsam, nemo mollitia adipisci fugiat repudiandae quae praesentium soluta veritatis fuga amet dolorem nulla!', 'http://stackoverflow.com/questions/521171/a-java-collection-of-value-pairs-tuples', '2019-01-23 08:44:16', '2019-01-23 08:44:16', '1', 'Lorem ipsum dolor sit amet, consectetur adipisicing elit. Rem alias illo eum quae cum enim aspernatur facere architecto, aliquid, et earum, assumenda laudantium adipisci nobis laboriosam itaque! Enim earum, officiis quae, fuga deserunt doloribus voluptate.', 'Prerequisites Development environment...', NULL, 1),
(16, 1, 'Lorem ipsum dolor sit amet, consectetur adipisicing elit. Quo quasi animi sunt est pariatur minima praesentium, laudantium quia architecto veritatis.', 'Lorem ipsum dolor sit amet, consectetur adipisicing elit. Blanditiis perspiciatis rerum ipsam, nemo mollitia adipisci fugiat repudiandae quae praesentium soluta veritatis fuga amet dolorem nulla!', 'http://stackoverflow.com/questions/521171/a-java-collection-of-value-pairs-tuples', '2019-01-23 08:44:16', '2019-01-23 08:44:16', '1', 'Lorem ipsum dolor sit amet, consectetur adipisicing elit. Rem alias illo eum quae cum enim aspernatur facere architecto, aliquid, et earum, assumenda laudantium adipisci nobis laboriosam itaque! Enim earum, officiis quae, fuga deserunt doloribus voluptate.', 'Prerequisites Development environment...', NULL, 1),
(17, 1, 'Lorem ipsum dolor sit amet, consectetur adipisicing elit. Quo quasi animi sunt est pariatur minima praesentium, laudantium quia architecto veritatis.', 'Lorem ipsum dolor sit amet, consectetur adipisicing elit. Blanditiis perspiciatis rerum ipsam, nemo mollitia adipisci fugiat repudiandae quae praesentium soluta veritatis fuga amet dolorem nulla!', 'http://stackoverflow.com/questions/521171/a-java-collection-of-value-pairs-tuples', '2019-01-23 08:44:16', '2019-01-23 08:44:16', '1', 'Lorem ipsum dolor sit amet, consectetur adipisicing elit. Rem alias illo eum quae cum enim aspernatur facere architecto, aliquid, et earum, assumenda laudantium adipisci nobis laboriosam itaque! Enim earum, officiis quae, fuga deserunt doloribus voluptate.', 'Prerequisites Development environment...', NULL, 1),
(18, 1, 'Lorem ipsum dolor sit amet, consectetur adipisicing elit. Quo quasi animi sunt est pariatur minima praesentium, laudantium quia architecto veritatis.', 'Lorem ipsum dolor sit amet, consectetur adipisicing elit. Blanditiis perspiciatis rerum ipsam, nemo mollitia adipisci fugiat repudiandae quae praesentium soluta veritatis fuga amet dolorem nulla!', 'http://stackoverflow.com/questions/521171/a-java-collection-of-value-pairs-tuples', '2019-01-23 08:44:16', '2019-01-23 08:44:16', '1', 'Lorem ipsum dolor sit amet, consectetur adipisicing elit. Rem alias illo eum quae cum enim aspernatur facere architecto, aliquid, et earum, assumenda laudantium adipisci nobis laboriosam itaque! Enim earum, officiis quae, fuga deserunt doloribus voluptate.', 'Prerequisites Development environment...', NULL, 1),
(19, 1, 'Lorem ipsum dolor sit amet, consectetur adipisicing elit. Quo quasi animi sunt est pariatur minima praesentium, laudantium quia architecto veritatis.', 'Lorem ipsum dolor sit amet, consectetur adipisicing elit. Blanditiis perspiciatis rerum ipsam, nemo mollitia adipisci fugiat repudiandae quae praesentium soluta veritatis fuga amet dolorem nulla!', 'http://stackoverflow.com/questions/521171/a-java-collection-of-value-pairs-tuples', '2019-01-23 08:44:16', '2019-01-23 08:44:16', '1', 'Lorem ipsum dolor sit amet, consectetur adipisicing elit. Rem alias illo eum quae cum enim aspernatur facere architecto, aliquid, et earum, assumenda laudantium adipisci nobis laboriosam itaque! Enim earum, officiis quae, fuga deserunt doloribus voluptate.', 'Prerequisites Development environment...', NULL, 1),
(20, 1, 'Lorem ipsum dolor sit amet, consectetur adipisicing elit. Quo quasi animi sunt est pariatur minima praesentium, laudantium quia architecto veritatis.', 'Lorem ipsum dolor sit amet, consectetur adipisicing elit. Blanditiis perspiciatis rerum ipsam, nemo mollitia adipisci fugiat repudiandae quae praesentium soluta veritatis fuga amet dolorem nulla!', 'http://stackoverflow.com/questions/521171/a-java-collection-of-value-pairs-tuples', '2019-01-23 08:44:16', '2019-01-23 08:44:16', '1', 'Lorem ipsum dolor sit amet, consectetur adipisicing elit. Rem alias illo eum quae cum enim aspernatur facere architecto, aliquid, et earum, assumenda laudantium adipisci nobis laboriosam itaque! Enim earum, officiis quae, fuga deserunt doloribus voluptate.', 'Prerequisites Development environment...', NULL, 1),
(21, 1, 'Lorem ipsum dolor sit amet, consectetur adipisicing elit. Quo quasi animi sunt est pariatur minima praesentium, laudantium quia architecto veritatis.', 'Lorem ipsum dolor sit amet, consectetur adipisicing elit. Blanditiis perspiciatis rerum ipsam, nemo mollitia adipisci fugiat repudiandae quae praesentium soluta veritatis fuga amet dolorem nulla!', 'http://stackoverflow.com/questions/521171/a-java-collection-of-value-pairs-tuples', '2019-01-23 08:44:16', '2019-01-23 08:44:16', '1', 'Lorem ipsum dolor sit amet, consectetur adipisicing elit. Rem alias illo eum quae cum enim aspernatur facere architecto, aliquid, et earum, assumenda laudantium adipisci nobis laboriosam itaque! Enim earum, officiis quae, fuga deserunt doloribus voluptate.', 'Prerequisites Development environment...', NULL, 1),
(22, 1, 'Lorem ipsum dolor sit amet, consectetur adipisicing elit. Quo quasi animi sunt est pariatur minima praesentium, laudantium quia architecto veritatis.', 'Lorem ipsum dolor sit amet, consectetur adipisicing elit. Blanditiis perspiciatis rerum ipsam, nemo mollitia adipisci fugiat repudiandae quae praesentium soluta veritatis fuga amet dolorem nulla!', 'http://stackoverflow.com/questions/521171/a-java-collection-of-value-pairs-tuples', '2019-01-23 08:44:16', '2019-01-23 08:44:16', '1', 'Lorem ipsum dolor sit amet, consectetur adipisicing elit. Rem alias illo eum quae cum enim aspernatur facere architecto, aliquid, et earum, assumenda laudantium adipisci nobis laboriosam itaque! Enim earum, officiis quae, fuga deserunt doloribus voluptate.', 'Prerequisites Development environment...', NULL, 1),
(23, 1, 'Lorem ipsum dolor sit amet, consectetur adipisicing elit. Quo quasi animi sunt est pariatur minima praesentium, laudantium quia architecto veritatis.', 'Lorem ipsum dolor sit amet, consectetur adipisicing elit. Blanditiis perspiciatis rerum ipsam, nemo mollitia adipisci fugiat repudiandae quae praesentium soluta veritatis fuga amet dolorem nulla!', 'http://stackoverflow.com/questions/521171/a-java-collection-of-value-pairs-tuples', '2019-01-23 08:44:16', '2019-01-23 08:44:16', '1', 'Lorem ipsum dolor sit amet, consectetur adipisicing elit. Rem alias illo eum quae cum enim aspernatur facere architecto, aliquid, et earum, assumenda laudantium adipisci nobis laboriosam itaque! Enim earum, officiis quae, fuga deserunt doloribus voluptate.', 'Prerequisites Development environment...', NULL, 1),
(24, 1, 'Lorem ipsum dolor sit amet, consectetur adipisicing elit. Quo quasi animi sunt est pariatur minima praesentium, laudantium quia architecto veritatis.', 'Lorem ipsum dolor sit amet, consectetur adipisicing elit. Blanditiis perspiciatis rerum ipsam, nemo mollitia adipisci fugiat repudiandae quae praesentium soluta veritatis fuga amet dolorem nulla!', 'http://stackoverflow.com/questions/521171/a-java-collection-of-value-pairs-tuples', '2019-01-23 08:44:16', '2019-01-23 08:44:16', '1', 'Lorem ipsum dolor sit amet, consectetur adipisicing elit. Rem alias illo eum quae cum enim aspernatur facere architecto, aliquid, et earum, assumenda laudantium adipisci nobis laboriosam itaque! Enim earum, officiis quae, fuga deserunt doloribus voluptate.', 'Prerequisites Development environment...', NULL, 1),
(25, 1, 'Lorem ipsum dolor sit amet, consectetur adipisicing elit. Quo quasi animi sunt est pariatur minima praesentium, laudantium quia architecto veritatis.', 'Lorem ipsum dolor sit amet, consectetur adipisicing elit. Blanditiis perspiciatis rerum ipsam, nemo mollitia adipisci fugiat repudiandae quae praesentium soluta veritatis fuga amet dolorem nulla!', 'http://stackoverflow.com/questions/521171/a-java-collection-of-value-pairs-tuples', '2019-01-23 08:44:16', '2019-01-23 08:44:16', '1', 'Lorem ipsum dolor sit amet, consectetur adipisicing elit. Rem alias illo eum quae cum enim aspernatur facere architecto, aliquid, et earum, assumenda laudantium adipisci nobis laboriosam itaque! Enim earum, officiis quae, fuga deserunt doloribus voluptate.', 'Prerequisites Development environment...', NULL, 1),
(26, 1, 'Lorem ipsum dolor sit amet, consectetur adipisicing elit. Quo quasi animi sunt est pariatur minima praesentium, laudantium quia architecto veritatis.', 'Lorem ipsum dolor sit amet, consectetur adipisicing elit. Blanditiis perspiciatis rerum ipsam, nemo mollitia adipisci fugiat repudiandae quae praesentium soluta veritatis fuga amet dolorem nulla!', 'http://stackoverflow.com/questions/521171/a-java-collection-of-value-pairs-tuples', '2019-01-23 08:44:16', '2019-01-23 08:44:16', '1', 'Lorem ipsum dolor sit amet, consectetur adipisicing elit. Rem alias illo eum quae cum enim aspernatur facere architecto, aliquid, et earum, assumenda laudantium adipisci nobis laboriosam itaque! Enim earum, officiis quae, fuga deserunt doloribus voluptate.', 'Prerequisites Development environment...', NULL, 1),
(27, 1, 'Lorem ipsum dolor sit amet, consectetur adipisicing elit. Quo quasi animi sunt est pariatur minima praesentium, laudantium quia architecto veritatis.', 'Lorem ipsum dolor sit amet, consectetur adipisicing elit. Blanditiis perspiciatis rerum ipsam, nemo mollitia adipisci fugiat repudiandae quae praesentium soluta veritatis fuga amet dolorem nulla!', 'http://stackoverflow.com/questions/521171/a-java-collection-of-value-pairs-tuples', '2019-01-23 08:44:16', '2019-01-23 08:44:16', '1', 'Lorem ipsum dolor sit amet, consectetur adipisicing elit. Rem alias illo eum quae cum enim aspernatur facere architecto, aliquid, et earum, assumenda laudantium adipisci nobis laboriosam itaque! Enim earum, officiis quae, fuga deserunt doloribus voluptate.', 'Prerequisites Development environment...', NULL, 1),
(28, 1, 'Lorem ipsum dolor sit amet, consectetur adipisicing elit. Quo quasi animi sunt est pariatur minima praesentium, laudantium quia architecto veritatis.', 'Lorem ipsum dolor sit amet, consectetur adipisicing elit. Blanditiis perspiciatis rerum ipsam, nemo mollitia adipisci fugiat repudiandae quae praesentium soluta veritatis fuga amet dolorem nulla!', 'http://stackoverflow.com/questions/521171/a-java-collection-of-value-pairs-tuples', '2019-01-23 08:44:16', '2019-01-23 08:44:16', '1', 'Lorem ipsum dolor sit amet, consectetur adipisicing elit. Rem alias illo eum quae cum enim aspernatur facere architecto, aliquid, et earum, assumenda laudantium adipisci nobis laboriosam itaque! Enim earum, officiis quae, fuga deserunt doloribus voluptate.', 'Prerequisites Development environment...', NULL, 1),
(29, 1, 'Lorem ipsum dolor sit amet, consectetur adipisicing elit. Quo quasi animi sunt est pariatur minima praesentium, laudantium quia architecto veritatis.', 'Lorem ipsum dolor sit amet, consectetur adipisicing elit. Blanditiis perspiciatis rerum ipsam, nemo mollitia adipisci fugiat repudiandae quae praesentium soluta veritatis fuga amet dolorem nulla!', 'http://stackoverflow.com/questions/521171/a-java-collection-of-value-pairs-tuples', '2019-01-23 08:44:16', '2019-01-23 08:44:16', '1', 'Lorem ipsum dolor sit amet, consectetur adipisicing elit. Rem alias illo eum quae cum enim aspernatur facere architecto, aliquid, et earum, assumenda laudantium adipisci nobis laboriosam itaque! Enim earum, officiis quae, fuga deserunt doloribus voluptate.', 'Prerequisites Development environment...', NULL, 1),
(30, 1, 'Lorem ipsum dolor sit amet, consectetur adipisicing elit. Quo quasi animi sunt est pariatur minima praesentium, laudantium quia architecto veritatis.', 'Lorem ipsum dolor sit amet, consectetur adipisicing elit. Blanditiis perspiciatis rerum ipsam, nemo mollitia adipisci fugiat repudiandae quae praesentium soluta veritatis fuga amet dolorem nulla!', 'http://stackoverflow.com/questions/521171/a-java-collection-of-value-pairs-tuples', '2019-01-23 08:44:16', '2019-01-23 08:44:16', '1', 'Lorem ipsum dolor sit amet, consectetur adipisicing elit. Rem alias illo eum quae cum enim aspernatur facere architecto, aliquid, et earum, assumenda laudantium adipisci nobis laboriosam itaque! Enim earum, officiis quae, fuga deserunt doloribus voluptate.', 'Prerequisites Development environment...', NULL, 1);