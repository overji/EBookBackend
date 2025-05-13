# eBook Backend

## 项目简介
eBook Backend 是一个基于 Spring Boot 的电子书管理系统后端，提供用户注册、登录、个人信息管理、购物车、订单管理等功能。

---

## 技术栈
- **语言**: Java
- **框架**: Spring Boot
- **构建工具**: Maven
- **数据库**: MySQL
- **安全**: Spring Security
- **其他**: Hibernate, Jakarta Servlet

---

## 功能模块
### 用户模块
- 用户注册
- 用户登录
- 修改密码
- 修改个人信息（头像、简介等）
- 地址管理

### 购物车模块
- 添加商品到购物车
- 查看购物车
- 删除购物车商品

### 订单模块
- 创建订单
- 查看订单
- 删除订单

---

## 项目结构
```
src/
├── main/
│   ├── java/com/overji/ebookbackend/
│   │   ├── ControlLayer/         # 控制层，处理 HTTP 请求
│   │   ├── ServiceLayer/         # 服务层，包含业务逻辑
│   │   ├── DataAccessLayer/      # 数据访问层，操作数据库
│   │   ├── EntityLayer/          # 实体层，定义数据库表结构
│   │   ├── Utils/                # 工具类
│   ├── resources/
│       ├── application.properties       # 配置文件
```

---

## 快速开始
### 环境要求
- JDK 17+
- Maven 3.8+
- MySQL 8.0+

### 本地运行
1. 克隆项目：
   ```bash
   git clone https://github.com/overji/EBookBackend.git
   cd ebook-backend
   ```

2. 配置数据库：
   在 `src/main/resources/application.yml` 中设置数据库连接信息：
   ```yaml
   spring:
     datasource:
       url: jdbc:mysql://localhost:3306/ebook
       username: your_username
       password: your_password
   ```

3. 启动项目：
   ```bash
   mvn spring-boot:run
   ```

4. 访问接口：
    - 默认接口地址: `http://localhost:8080`

---

## API 文档
### 用户相关
- **注册用户**: `POST /api/user/register`
- **登录用户**: `POST /api/user/login`
- **获取用户信息**: `GET /api/user/me`
- **修改密码**: `PUT /api/user/me/password`
- **上传头像**: `POST /api/user/me/avatar`

### 地址管理
- **添加地址**: `POST /api/user/me/addresses`
- **获取地址**: `GET /api/user/me/addresses`
- **删除地址**: `DELETE /api/user/me/address/{userAddrId}`

---

## 项目配置
### CORS 配置
允许前端跨域访问：
- 来源: `http://localhost:3000`
- 方法: `GET`, `POST`, `PUT`, `DELETE`, `OPTIONS`

### 文件上传
用户头像上传路径：
- 本地路径: `./avatars/`
- 映射路径: `/api/user/avatars/**`

---

## 贡献
欢迎提交 Issue 或 Pull Request 来改进项目。

---

## 许可证
本项目基于 MIT 许可证开源。