-- MySQL dump 10.13  Distrib 8.4.0, for Linux (x86_64)
--
-- Host: localhost    Database: ebookdb
-- ------------------------------------------------------
-- Server version	8.4.0

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8mb4 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `book_tag`
--

DROP TABLE IF EXISTS `book_tag`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `book_tag` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `book_tag_id` bigint NOT NULL,
  `name` tinytext COLLATE utf8mb4_general_ci,
  `book_id` bigint NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FKbacbwwmfdh1bwsdgs9pycw9rr` (`book_id`),
  CONSTRAINT `FKbacbwwmfdh1bwsdgs9pycw9rr` FOREIGN KEY (`book_id`) REFERENCES `books` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=97 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `book_tag`
--

LOCK TABLES `book_tag` WRITE;
/*!40000 ALTER TABLE `book_tag` DISABLE KEYS */;
INSERT INTO `book_tag` VALUES (4,0,'BanG Dream',2),(5,1,'漫画',2),(6,2,'画集',2),(7,3,'动漫',2),(8,0,'漫画',3),(9,1,'可爱',3),(10,0,'漫画',4),(11,1,'可爱',4),(15,0,'技术类',6),(16,1,'C++',6),(17,2,'编程',6),(21,0,'技术类',8),(22,1,'go',8),(23,2,'编程',8),(24,0,'技术类',9),(25,1,'操作系统',9),(26,0,'社会科学',10),(27,1,'经济学',10),(28,0,'小说',11),(29,1,'文学',11),(56,0,'Kubernetes',25),(57,1,'运维',25),(58,2,'软件工程',25),(75,0,'机器学习',27),(76,1,'python',27),(86,0,'BanG Dream',1),(87,1,'漫画',1),(88,2,'画集',1),(89,0,'技术类',7),(90,1,'go',7),(91,2,'编程',7),(94,0,'技术类',5),(95,1,'Java',5),(96,2,'编程',5);
/*!40000 ALTER TABLE `book_tag` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `books`
--

DROP TABLE IF EXISTS `books`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `books` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `author` tinytext COLLATE utf8mb4_general_ci,
  `cover` tinytext COLLATE utf8mb4_general_ci,
  `description` text COLLATE utf8mb4_general_ci,
  `price` bigint DEFAULT NULL,
  `sales` bigint DEFAULT NULL,
  `title` tinytext COLLATE utf8mb4_general_ci,
  `isbn` varchar(255) COLLATE utf8mb4_general_ci DEFAULT NULL,
  `stock` bigint DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=29 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `books`
--

LOCK TABLES `books` WRITE;
/*!40000 ALTER TABLE `books` DISABLE KEYS */;
INSERT INTO `books` VALUES (1,'日本FAMI通App编辑部','https://img3m9.ddimg.cn/7/30/11746687399-1_w_1714459113.jpg','收录从简体中文版游戏2020年9月下旬~2021年5月上旬，在游戏活动中实装的超过300张插图！包括CD专辑封面及主题联动插图哦！',14500,57,'BanG Dream！少女乐团派对！官方视觉设定集. 03 游戏原画插图bangdream手游插画集画册设定集天闻角川日本二次元书','978-3-16-148410-0',114509),(2,'日本FAMI通App编辑部','https://img3m2.ddimg.cn/91/8/11756168812-1_w_1716608274.jpg','收录游戏卡牌插图及相关视觉插图的粉丝的官方视觉设定集！224页丰富内容！收录从简体中文版游戏2020年1月~2020年9月中旬，在游戏活动中实装的超过300张插图！包括CD专辑封面及主题联动插图哦！！',9925,157,'BanG Dream！少女乐团派对！官方视觉设定集. 02 游戏原画插图bangdream手游插画集画册设定集天闻角川日本二次元书','978-3-16-148410-0',9999),(3,'nagano','https://img3m0.ddimg.cn/79/23/29803930-1_w_1730369763.jpg','那些又小又可爱的家伙们--以吉伊卡哇为首的一群小生物，围绕着它们展开的快乐的、伤心的、有点艰难的日常故事。本想过上被大家爱着、呵护着的生活可周围却有许多来路不明的存在?!不过，,幸好有最好的朋友哈奇喵、乌萨奇陪伴努力生活的吉伊卡哇身边总是洋溢着笑容。',3360,22,'吉伊卡哇:那些又小又可爱的家伙们.1（简体中文版） 超人气IP“chiikawa”原作漫画第一册','978-3-16-148410-0',10000),(4,'nagano','https://img3m8.ddimg.cn/40/7/11520575788-1_w_1735262207.jpg','那些又小又可爱的家伙们--以吉伊卡哇为首的一群小生物，围绕着它们展开的快乐的、伤心的、有点艰难的日常故事。本想过上被大家爱着、呵护着的生活可周围却有许多来路不明的存在?!不过，,幸好有最好的朋友哈奇喵、乌萨奇陪伴努力生活的吉伊卡哇身边总是洋溢着笑容。',6600,1,'吉伊卡哇:那些又小又可爱的家伙们.2（简体中文版） 超人气IP“chiikawa”原作漫画第一册','978-3-16-148410-0',10000),(5,'[美] 凯·S.霍斯特曼（Cay S.Horstmann）','https://img3m8.ddimg.cn/7/0/29411818-1_w_28.jpg','畅销20载的大师之作，Jolt大奖得主，全球百万Java工程师口碑选择，Java事实标准，提供部分作者亲授视频+海量代码集',11170,37,'Java核心技术 卷I：开发基础（原书第12版）','978-3-16-148410-0',0),(6,'(美)李普曼 等','https://img3m2.ddimg.cn/33/18/23321562-1_w_24.jpg','C++学习头牌 全球读者千万 全面采用新标 技术影响力图书冠军',12800,27,'C++ Primer中文版（第5版）','978-3-16-148410-0',10000),(7,'[中国]历冰;朱荣鑫;黄迪璇','https://img3m1.ddimg.cn/95/9/29556941-1_w_1.jpg','《深入Go语言：原理、关键技术与实战》（深入了解Go语言的实现内幕、高级特性以及实践中的使用陷阱，真实体验Go语言的简洁和高效）',7420,138,'深入Go语言：原理、关键技术与实战','978-3-16-148420-0',11451),(8,'明日科技','https://img3m0.ddimg.cn/43/36/29712220-1_w_1713770406.jpg','Go语言入门经典，Go语言开发者的入门选择，100集微课视频，98个应用实例，学习1小时，训练10小时，从入门到项目实战，打造全新学习生态。',8530,7,'Go语言从入门到精通','978-3-16-148410-0',10000),(9,'陈海波','https://img3m5.ddimg.cn/78/3/29516235-1_w_1739415193.jpg','面向基础理论、前沿研究与工业界实践，以ARM架构为主，微内核操作系统ChCore全新升级，配有全套教学资源',8920,7,'操作系统：原理与实现','978-3-16-148410-0',10000),(10,'（美）曼昆','https://img3m3.ddimg.cn/48/15/23697183-1_w_1.jpg','哈佛大学曼昆教授扛鼎之作，广受欢迎的经济学入门读物，带你迈进经济学的殿堂！',7680,6,'经济学原理(第7版)套装 (微观经济学分册+宏观经济学分册) 曼昆著','978-3-16-148410-0',10000),(11,'肖洛霍夫 著，力冈 译','https://img3m0.ddimg.cn/58/10/28542550-1_w_6.jpg','《静静的顿河》是关于战争与民族苦难历程的史诗，是描绘和平、土地、劳动的充满灵性的巨幅画卷。',11520,14,'静静的顿河','978-3-16-148410-0',10000),(25,'杜宽','https://img3m4.ddimg.cn/90/34/29404674-1_w_1.jpg','超过60万学员热棒，一线K8s工程师潜心之作，众多业界专家力荐',13110,4,'云原生Kubernetes全栈架构师实战','9787302603887',114514),(27,'[美]杰夫·普罗西斯（Jeff Prosise）著；周靖 译；[印]阿什温·帕扬卡 (Ashwin Pajankar) ， 阿迪亚·乔希','https://img3m4.ddimg.cn/42/19/29813694-1_w_1732170014.jpg','一套五册经典，解释人工智能和机器学习的落地应用场景。助力数据科学家、机器学习工程师以及Python软件工程师迅速掌握新技能。',45921,0,'Python人工智能与机器学习（套装全5册)','9000302002819',114514);
/*!40000 ALTER TABLE `books` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `cart`
--

DROP TABLE IF EXISTS `cart`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `cart` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `number` bigint NOT NULL,
  `user_cart_id` bigint NOT NULL,
  `book_id` bigint NOT NULL,
  `user_id` bigint NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FK1ykovbj90wkvqwa6m6463h21p` (`book_id`),
  KEY `FKg5uhi8vpsuy0lgloxk2h4w5o6` (`user_id`),
  CONSTRAINT `FK1ykovbj90wkvqwa6m6463h21p` FOREIGN KEY (`book_id`) REFERENCES `books` (`id`),
  CONSTRAINT `FKg5uhi8vpsuy0lgloxk2h4w5o6` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=16 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `cart`
--

LOCK TABLES `cart` WRITE;
/*!40000 ALTER TABLE `cart` DISABLE KEYS */;
INSERT INTO `cart` VALUES (8,1,0,1,5);
/*!40000 ALTER TABLE `cart` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `comment_likes`
--

DROP TABLE IF EXISTS `comment_likes`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `comment_likes` (
  `comment_id` bigint NOT NULL,
  `user_id` bigint NOT NULL,
  KEY `FK6h3lbneryl5pyb9ykaju7werx` (`user_id`),
  KEY `FK3wa5u7bs1p1o9hmavtgdgk1go` (`comment_id`),
  CONSTRAINT `FK3wa5u7bs1p1o9hmavtgdgk1go` FOREIGN KEY (`comment_id`) REFERENCES `comments` (`id`),
  CONSTRAINT `FK6h3lbneryl5pyb9ykaju7werx` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `comment_likes`
--

LOCK TABLES `comment_likes` WRITE;
/*!40000 ALTER TABLE `comment_likes` DISABLE KEYS */;
INSERT INTO `comment_likes` VALUES (1,7);
/*!40000 ALTER TABLE `comment_likes` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `comments`
--

DROP TABLE IF EXISTS `comments`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `comments` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `content` text COLLATE utf8mb4_general_ci,
  `created_at` datetime(6) NOT NULL,
  `reply` text COLLATE utf8mb4_general_ci,
  `book_id` bigint NOT NULL,
  `user_id` bigint NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FK1ey8gegnanvybix5a025vepf4` (`book_id`),
  KEY `FK8omq0tc18jd43bu5tjh6jvraq` (`user_id`),
  CONSTRAINT `FK1ey8gegnanvybix5a025vepf4` FOREIGN KEY (`book_id`) REFERENCES `books` (`id`),
  CONSTRAINT `FK8omq0tc18jd43bu5tjh6jvraq` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `comments`
--

LOCK TABLES `comments` WRITE;
/*!40000 ALTER TABLE `comments` DISABLE KEYS */;
INSERT INTO `comments` VALUES (1,'哈哈','2025-05-27 13:09:33.730054','',1,6),(2,'666','2025-05-27 13:13:46.449413','',1,7);
/*!40000 ALTER TABLE `comments` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `order_item`
--

DROP TABLE IF EXISTS `order_item`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `order_item` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `number` bigint NOT NULL,
  `book_id` bigint NOT NULL,
  `order_in_id` bigint NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FKegdk0kfrrdr1248hq5ixgr5x6` (`book_id`),
  KEY `FKb6jjegw7xwlwsk2ti3sxqpgq9` (`order_in_id`),
  CONSTRAINT `FKb6jjegw7xwlwsk2ti3sxqpgq9` FOREIGN KEY (`order_in_id`) REFERENCES `orders` (`id`),
  CONSTRAINT `FKegdk0kfrrdr1248hq5ixgr5x6` FOREIGN KEY (`book_id`) REFERENCES `books` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=20 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `order_item`
--

LOCK TABLES `order_item` WRITE;
/*!40000 ALTER TABLE `order_item` DISABLE KEYS */;
INSERT INTO `order_item` VALUES (2,5,1,2),(3,2,6,2),(4,1,3,4),(5,1,4,4),(6,4,1,5),(7,4,6,5),(8,1,2,6),(9,5,7,7),(10,1,1,8),(11,10,5,9),(12,3,1,10),(13,4,25,11),(14,3,10,11),(15,3,9,12),(16,4,6,12),(17,1,5,13),(18,2,1,14),(19,2,7,14);
/*!40000 ALTER TABLE `order_item` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `orders`
--

DROP TABLE IF EXISTS `orders`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `orders` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `address` text COLLATE utf8mb4_general_ci,
  `created_at` datetime(6) NOT NULL,
  `receiver` text COLLATE utf8mb4_general_ci,
  `tel` text COLLATE utf8mb4_general_ci,
  `user_order_id` bigint NOT NULL,
  `user_id` bigint NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FK32ql8ubntj5uh44ph9659tiih` (`user_id`),
  CONSTRAINT `FK32ql8ubntj5uh44ph9659tiih` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=15 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `orders`
--

LOCK TABLES `orders` WRITE;
/*!40000 ALTER TABLE `orders` DISABLE KEYS */;
INSERT INTO `orders` VALUES (2,'凹分','2025-05-27 13:10:28.222455','王','激发u法国',0,6),(4,'afefsf','2025-05-27 13:13:03.860730','weqawf','rwawfw',1,6),(5,'a','2025-05-27 13:14:03.694164','ccc','b',0,7),(6,'扣分覅','2025-05-27 14:17:27.007298','思考过后违规','问题集合完毕v妇女',1,7),(7,'飞机卡尔部分','2025-05-27 14:17:34.414385','傅海峰','发货和我并非你看',2,7),(8,'a','2025-05-27 15:36:38.189467','许可','18439423501',0,5),(9,'和我i飞鸟时代步伐','2025-05-29 11:10:21.231020','啊十分被动恩情基本恢复','外设高峰会你曾经',3,7),(10,'凹分segsd','2025-06-01 10:23:07.971810','王fgrwe','激发u法国wreggdfs',2,6),(11,'qgyufqi','2025-06-01 11:03:41.063964','yfjhbewduh','vfujkwnbevgyfwhuiejn',3,6),(12,'安徽','2025-06-03 13:21:08.532094','ha','012045201',0,23),(13,'和我i飞鸟时代步伐','2025-06-03 13:31:26.155113','啊十分被动恩情基本恢复','qeg',0,25),(14,'rodhgwo','2025-06-03 13:32:25.042286','wjosb y7ujew','ghkriogji',1,25);
/*!40000 ALTER TABLE `orders` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user_address`
--

DROP TABLE IF EXISTS `user_address`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `user_address` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `address` tinytext COLLATE utf8mb4_general_ci NOT NULL,
  `phone` tinytext COLLATE utf8mb4_general_ci NOT NULL,
  `receiver` tinytext COLLATE utf8mb4_general_ci NOT NULL,
  `user_addr_id` bigint NOT NULL,
  `uid` bigint NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FKcf73it2qh8miua9osj685t1vx` (`uid`),
  CONSTRAINT `FKcf73it2qh8miua9osj685t1vx` FOREIGN KEY (`uid`) REFERENCES `users` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user_address`
--

LOCK TABLES `user_address` WRITE;
/*!40000 ALTER TABLE `user_address` DISABLE KEYS */;
INSERT INTO `user_address` VALUES (1,'凹分','激发u法国','王',0,6),(2,'飞机卡尔部分','发货和我并非你看','傅海峰',0,7),(3,'扣分覅','问题集合完毕v妇女','思考过后违规',1,7),(4,'安徽','012045201','ha',0,23);
/*!40000 ALTER TABLE `user_address` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user_auth`
--

DROP TABLE IF EXISTS `user_auth`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `user_auth` (
  `user_id` bigint NOT NULL,
  `password` varchar(255) COLLATE utf8mb4_general_ci NOT NULL,
  PRIMARY KEY (`user_id`),
  CONSTRAINT `FK9hog4tmdalcddidanohglx6gl` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user_auth`
--

LOCK TABLES `user_auth` WRITE;
/*!40000 ALTER TABLE `user_auth` DISABLE KEYS */;
INSERT INTO `user_auth` VALUES (1,'$2a$10$kgDXfhunU9wN3tR7QzAHXe1mUSdfWT33KkUd2dkdtgbErGLC87dju'),(5,'$2a$10$eWyQ70tl2VSt/J.dZpL2OeD97O2/lTMdUSojlht/kKmdx.w.SQjJi'),(6,'$2a$10$3PhsWd68utww8Crx5ZpMreC9gWx1z.qrNVBLAE876FKAuGrqYAkCO'),(7,'$2a$10$9stKiPZb1GGQMwTCrKfaFuNvKLlpnbAysez8TDRHIcRqPG8K1ONpa'),(21,'$2a$10$isLzXkIExcsdBkUWM0iVMuds4Xs1UvwxFmf7aEFK80BumIXliECY.'),(23,'$2a$10$wPvf4g3TSJQP.d4.o/1fj.rfRX0TuZiniitXQPv9Pgr2rH/n7XKSe'),(25,'$2a$10$Z3WeOZ5bTxNXQPZ3aCt1M.D2Y4EyoWeOg2ndoIQB8x23Kn9nOmLN2');
/*!40000 ALTER TABLE `user_auth` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `users`
--

DROP TABLE IF EXISTS `users`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `users` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `avatar` tinytext COLLATE utf8mb4_general_ci,
  `balance` bigint NOT NULL,
  `created_at` datetime(6) NOT NULL,
  `email` varchar(255) COLLATE utf8mb4_general_ci DEFAULT NULL,
  `introduction` tinytext COLLATE utf8mb4_general_ci,
  `nickname` varchar(255) COLLATE utf8mb4_general_ci NOT NULL,
  `username` varchar(255) COLLATE utf8mb4_general_ci NOT NULL,
  `is_disabled` bit(1) NOT NULL,
  `user_privilege` bigint NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UKr43af9ap4edm43mmtq01oddj6` (`username`)
) ENGINE=InnoDB AUTO_INCREMENT=26 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `users`
--

LOCK TABLES `users` WRITE;
/*!40000 ALTER TABLE `users` DISABLE KEYS */;
INSERT INTO `users` VALUES (1,'1747704885655.jpg',1000000,'2025-05-20 09:34:35.622284','overji1@sjtu.edu.cn','','overJi\'s nickname','overji',_binary '\0',1),(5,'1747719584022.jpg',976180,'2025-05-20 13:38:40.422119','overji1@sjtu.edu.cn','','overJi\'s nickname','overji_user',_binary '',0),(6,'1748322565700.jpg',772960,'2025-05-27 13:08:48.457115','demo@aaa.com','','demo_nick','demo',_binary '\0',0),(7,'1748322820663.jpg',732075,'2025-05-27 13:13:30.077142','demo1@aaa.com','','demo_nick','demo1',_binary '\0',0),(21,'',1000000,'2025-06-03 13:18:51.487065','afvbjn@si.cn','','shgbniwe','afhhiqn',_binary '\0',0),(23,'1748928018152.jpg',922040,'2025-06-03 13:19:25.132802','afvbjn@si.cn','苟利国家生死以','overji_user','overjiUser',_binary '\0',0),(25,'1748928666580.jpg',944990,'2025-06-03 13:25:40.335539','ewgfy8@sf.co','','富哥','rich',_binary '\0',0);
/*!40000 ALTER TABLE `users` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping events for database 'ebookdb'
--

--
-- Dumping routines for database 'ebookdb'
--
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2025-06-05 19:58:29
