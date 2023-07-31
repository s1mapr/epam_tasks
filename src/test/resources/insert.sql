
INSERT INTO `gift_certificate` VALUES (4,'test2','changed description',56.5,21,'2023-04-19T19:12Z','2023-04-27T21:13:57.694'),(5,'test4','some description',56.5,21,'2023-04-19T22:27:20.693','2023-04-19T22:27:20.693'),(6,'test5','changed description',56.5,21,'2023-04-20T13:22:00.218','2023-04-20T21:41:47.266'),(7,'test6','some description',56.5,21,'2023-04-21T18:01:31.014','2023-04-21T18:01:31.014'),(8,'test7','changed description',56.5,21,'2023-04-22T14:21:33.511','2023-04-26T11:14:29.520'),(9,'test8','some description',56.5,21,'2023-04-22T18:56:24.835','2023-04-22T18:56:24.835'),(10,'test9','some description',56.5,21,'2023-04-27T21:55:20.129','2023-04-27T21:55:20.129'),(11,'test10','some description',56.5,21,'2023-04-27T21:57:54.097','2023-04-27T21:57:54.097');

INSERT INTO `tag` VALUES (4,'300$'),(5,'gift'),(6,'base'),(7,'newTest'),(8,'anotherTest');

INSERT INTO `certificate_tag` VALUES (4,4),(5,5),(4,5),(5,4);

INSERT INTO `user` VALUES (1, 'userName');

INSERT INTO `orders` VALUES (1, 4, 1, '30.07.2023', 12.5), (2, 5, 1, '30.07.2023', 18.5);