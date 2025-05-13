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
) ENGINE=InnoDB AUTO_INCREMENT=30 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `book_tag`
--

LOCK TABLES `book_tag` WRITE;
/*!40000 ALTER TABLE `book_tag` DISABLE KEYS */;
INSERT INTO `book_tag` VALUES (1,0,'BanG Dream',1),(2,1,'漫画',1),(3,2,'画集',1),(4,0,'BanG Dream',2),(5,1,'漫画',2),(6,2,'画集',2),(7,3,'动漫',2),(8,0,'漫画',3),(9,1,'可爱',3),(10,0,'漫画',4),(11,1,'可爱',4),(12,0,'技术类',5),(13,1,'Java',5),(14,2,'编程',5),(15,0,'技术类',6),(16,1,'C++',6),(17,2,'编程',6),(18,0,'技术类',7),(19,1,'go',7),(20,2,'编程',7),(21,0,'技术类',8),(22,1,'go',8),(23,2,'编程',8),(24,0,'技术类',9),(25,1,'操作系统',9),(26,0,'社会科学',10),(27,1,'经济学',10),(28,0,'小说',11),(29,1,'文学',11);
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
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `books`
--

LOCK TABLES `books` WRITE;
/*!40000 ALTER TABLE `books` DISABLE KEYS */;
INSERT INTO `books` VALUES (1,'日本FAMI通App编辑部','https://img3m9.ddimg.cn/7/30/11746687399-1_w_1714459113.jpg','收录从简体中文版游戏2020年9月下旬~2021年5月上旬，在游戏活动中实装的超过300张插图！包括CD专辑封面及主题联动插图哦！',14500,27,'BanG Dream！少女乐团派对！官方视觉设定集. 03 游戏原画插图bangdream手游插画集画册设定集天闻角川日本二次元书'),(2,'日本FAMI通App编辑部','https://img3m2.ddimg.cn/91/8/11756168812-1_w_1716608274.jpg','收录游戏卡牌插图及相关视觉插图的粉丝的官方视觉设定集！224页丰富内容！收录从简体中文版游戏2020年1月~2020年9月中旬，在游戏活动中实装的超过300张插图！包括CD专辑封面及主题联动插图哦！！',9925,148,'BanG Dream！少女乐团派对！官方视觉设定集. 02 游戏原画插图bangdream手游插画集画册设定集天闻角川日本二次元书'),(3,'nagano','https://img3m0.ddimg.cn/79/23/29803930-1_w_1730369763.jpg','那些又小又可爱的家伙们--以吉伊卡哇为首的一群小生物，围绕着它们展开的快乐的、伤心的、有点艰难的日常故事。本想过上被大家爱着、呵护着的生活可周围却有许多来路不明的存在?!不过，,幸好有最好的朋友哈奇喵、乌萨奇陪伴努力生活的吉伊卡哇身边总是洋溢着笑容。',3360,3,'吉伊卡哇:那些又小又可爱的家伙们.1（简体中文版） 超人气IP“chiikawa”原作漫画第一册'),(4,'nagano','https://img3m8.ddimg.cn/40/7/11520575788-1_w_1735262207.jpg','那些又小又可爱的家伙们--以吉伊卡哇为首的一群小生物，围绕着它们展开的快乐的、伤心的、有点艰难的日常故事。本想过上被大家爱着、呵护着的生活可周围却有许多来路不明的存在?!不过，,幸好有最好的朋友哈奇喵、乌萨奇陪伴努力生活的吉伊卡哇身边总是洋溢着笑容。',6600,0,'吉伊卡哇:那些又小又可爱的家伙们.2（简体中文版） 超人气IP“chiikawa”原作漫画第一册'),(5,'[美] 凯·S.霍斯特曼（Cay S.Horstmann）','https://img3m8.ddimg.cn/7/0/29411818-1_w_28.jpg','畅销20载的大师之作，Jolt大奖得主，全球百万Java工程师口碑选择，Java事实标准，提供部分作者亲授视频+海量代码集',11170,15,'Java核心技术 卷I：开发基础（原书第12版）'),(6,'(美)李普曼 等','https://img3m2.ddimg.cn/33/18/23321562-1_w_24.jpg','C++学习头牌 全球读者千万 全面采用新标 技术影响力图书冠军',12800,10,'C++ Primer中文版（第5版）'),(7,'[中国]历冰;朱荣鑫;黄迪璇','https://img3m1.ddimg.cn/95/9/29556941-1_w_1.jpg','《深入Go语言：原理、关键技术与实战》（深入了解Go语言的实现内幕、高级特性以及实践中的使用陷阱，真实体验Go语言的简洁和高效）',7420,18,'深入Go语言：原理、关键技术与实战'),(8,'明日科技','https://img3m0.ddimg.cn/43/36/29712220-1_w_1713770406.jpg','Go语言入门经典，Go语言开发者的入门选择，100集微课视频，98个应用实例，学习1小时，训练10小时，从入门到项目实战，打造全新学习生态。',8530,5,'Go语言从入门到精通'),(9,'陈海波','https://img3m5.ddimg.cn/78/3/29516235-1_w_1739415193.jpg','面向基础理论、前沿研究与工业界实践，以ARM架构为主，微内核操作系统ChCore全新升级，配有全套教学资源',8920,3,'操作系统：原理与实现'),(10,'（美）曼昆','https://img3m3.ddimg.cn/48/15/23697183-1_w_1.jpg','哈佛大学曼昆教授扛鼎之作，广受欢迎的经济学入门读物，带你迈进经济学的殿堂！',7680,3,'经济学原理(第7版)套装 (微观经济学分册+宏观经济学分册) 曼昆著'),(11,'肖洛霍夫 著，力冈 译','https://img3m0.ddimg.cn/58/10/28542550-1_w_6.jpg','《静静的顿河》是关于战争与民族苦难历程的史诗，是描绘和平、土地、劳动的充满灵性的巨幅画卷。',11520,5,'静静的顿河');
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
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `cart`
--

LOCK TABLES `cart` WRITE;
/*!40000 ALTER TABLE `cart` DISABLE KEYS */;
INSERT INTO `cart` VALUES (2,3,0,8,4),(3,5,1,2,4),(4,5,2,5,4);
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
INSERT INTO `comment_likes` VALUES (1,4),(3,4),(4,4),(4,6),(1,6),(8,6);
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
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `comments`
--

LOCK TABLES `comments` WRITE;
/*!40000 ALTER TABLE `comments` DISABLE KEYS */;
INSERT INTO `comments` VALUES (1,'大家好啊','2025-05-13 09:09:28.711282','',1,4),(2,'我喜欢你','2025-05-13 09:12:22.797490','overji',1,5),(3,'大家我都喜欢哦','2025-05-13 09:12:30.024960','',1,5),(4,'我也喜欢你','2025-05-13 09:13:56.141721','jimijijiji',1,4),(5,'你好','2025-05-13 09:15:31.261389','',1,6),(6,'a','2025-05-13 09:15:52.843031','',1,6),(7,'b','2025-05-13 09:15:55.132245','',1,6),(8,'hu','2025-05-13 09:16:13.938002','',10,6);
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
  `order_id` bigint NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FKegdk0kfrrdr1248hq5ixgr5x6` (`book_id`),
  KEY `FKb6jjegw7xwlwsk2ti3sxqpgq9` (`order_in_id`),
  KEY `FKt4dc2r9nbvbujrljv3e23iibt` (`order_id`),
  CONSTRAINT `FKb6jjegw7xwlwsk2ti3sxqpgq9` FOREIGN KEY (`order_in_id`) REFERENCES `orders` (`id`),
  CONSTRAINT `FKegdk0kfrrdr1248hq5ixgr5x6` FOREIGN KEY (`book_id`) REFERENCES `books` (`id`),
  CONSTRAINT `FKt4dc2r9nbvbujrljv3e23iibt` FOREIGN KEY (`order_id`) REFERENCES `orders` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `order_item`
--

LOCK TABLES `order_item` WRITE;
/*!40000 ALTER TABLE `order_item` DISABLE KEYS */;
INSERT INTO `order_item` VALUES (1,15,7,1,1),(2,5,1,2,2),(3,3,9,3,3),(4,5,1,3,3);
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
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `orders`
--

LOCK TABLES `orders` WRITE;
/*!40000 ALTER TABLE `orders` DISABLE KEYS */;
INSERT INTO `orders` VALUES (1,'上海交通大学','2025-05-13 09:10:37.263833','丸山彩','119',0,4),(2,'上海交通大学','2025-05-13 09:10:53.859644','dkl','120',1,4),(3,'bzd','2025-05-13 09:13:01.592444','123','a',0,5);
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
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user_address`
--

LOCK TABLES `user_address` WRITE;
/*!40000 ALTER TABLE `user_address` DISABLE KEYS */;
INSERT INTO `user_address` VALUES (1,'上海交通大学','119','丸山彩',0,4),(2,'上海交通大学','120','dkl',1,4);
/*!40000 ALTER TABLE `user_address` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user_auth`
--

DROP TABLE IF EXISTS `user_auth`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `user_auth` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `password` varchar(255) COLLATE utf8mb4_general_ci NOT NULL,
  `user_id` bigint NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UKnd1cbjapgbp1cohkrlhq2jky4` (`user_id`),
  CONSTRAINT `FK9hog4tmdalcddidanohglx6gl` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user_auth`
--

LOCK TABLES `user_auth` WRITE;
/*!40000 ALTER TABLE `user_auth` DISABLE KEYS */;
INSERT INTO `user_auth` VALUES (1,'$2a$10$EsPuqPBPZ5aJskqyiRXsg.SmwADqZFcXh89UIkKUajGCJOjv8YoSG',4),(2,'$2a$10$xaUH199/P7KdmfBLLEpEHu0yoac5SkJdEs3VNpc2qwmlMG3SNyYYq',5),(3,'$2a$10$JAFQyJ50ZOM/Ff6hkkBdgOCL/xydBfoOvO.99D6lIg1pd7M.c0R9q',6);
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
  PRIMARY KEY (`id`),
  UNIQUE KEY `UKr43af9ap4edm43mmtq01oddj6` (`username`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `users`
--

LOCK TABLES `users` WRITE;
/*!40000 ALTER TABLE `users` DISABLE KEYS */;
INSERT INTO `users` VALUES (4,'1747098548724.jpg',816200,'2025-05-13 09:08:43.626439','overji1@sjtu.edu.cn','','Ji','overji'),(5,'1747098734485.jpg',900740,'2025-05-13 09:11:37.429757','jimi@ji.net','我是谁，谁是我','ji666','jimijijiji'),(6,'1747098923522.jpg',1000000,'2025-05-13 09:14:53.377510','jimi@ji.net','','ji6666','overjiji');
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

-- Dump completed on 2025-05-13  9:42:22
