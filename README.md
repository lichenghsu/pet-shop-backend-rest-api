# Spring Boot Backend RESTful API

本專案為 [Pet Shop 電商平台](https://github.com/lichenghsu/pet-shop-vue-frontend) 的後端系統，採用 Spring Boot 3 建構，提供完整 RESTful API，搭配 Vue 前端專案可部署為一個完整的電商管理平台 Demo。

本系統重構自 [CGA104G1 專題](https://github.com/lichenghsu/CGA104G1-pet-shop)，以現代化技術堆疊實作，具備會員管理、商品管理、訂單處理、標籤分類、評論審核等功能，並整合 Swagger API 文件、Redis 快取與 Podman 容器部署。

---

## Demo 展示

- 前端 Repo：[pet-shop-vue-frontend](https://github.com/lichenghsu/pet-shop-vue-frontend)
- Swagger API：[http://localhost:8080/swagger-ui/index.html](http://localhost:8080/swagger-ui/index.html)

---

## 技術堆疊

- Spring Boot 3 / JPA / Hibernate
- Spring Security + JWT
- MySQL / Redis / Adminer
- Maven / Jib（容器打包）
- Podman（容器部署）
- Swagger + SpringDoc OpenAPI
- Lombok / DTO 分層架構

---

## 專案結構

```text
src/
├── config/         # 安全與跨域設定
├── controller/     # REST API 控制器
├── dto/            # 請求與回應封裝物件
├── entity/         # 資料庫實體模型
├── repository/     # 資料存取層
├── service/        # 商業邏輯
├── security/       # 使用者與 JWT 安全機制
└── exception/      # 全域錯誤處理
└── PetShopApplication.java
```

---

## 專案啟動方式

### 使用 Podman 啟動 MySQL / Redis

安裝（Ubuntu）：
```bash
sudo apt install podman podman-compose
```

啟動容器服務：
```bash
podman-compose up -d
```
將會啟動：

- `mysql`：資料庫服務，port `3306`
- `redis`：快取服務，port `6379`
- `adminer`：資料庫圖形介面，port `8081`

停止與清除容器:
```bash
podman-compose down
```

---

## 資料庫測試連線（MySQL）

```bash
mysql -h 127.0.0.1 -P 3306 -u root -p
```

---

## 啟動 Spring Boot 後端

使用開發模式（預設 port: 8080）：
```bash
./mvnw spring-boot:run -Dspring-boot.run.profiles=dev
```
正式環境：
```bash
./mvnw spring-boot:run -Dspring-boot.run.profiles=prod
```

或設定環境變數：
```bash
export SPRING_PROFILES_ACTIVE=prod
```

- 預設啟動埠號為：`8080`

可透過 Swagger 查看所有 API 文件：
- http://localhost:8080/swagger-ui/index.html

---

## 容器部署

打包成容器映像：
```bash
./mvnw compile jib:dockerBuild
```

建議平台：
- Render
- Railway
- Fly.io

---

## 前端整合與部署

請參考：[pet-shop-vue-frontend](https://github.com/lichenghsu/pet-shop-vue-frontend?tab=readme-ov-file)

前端採 Vue 3 + TypeScript + Naive UI 開發，支援：
- 商品管理（CRUD + 圖片上傳）
- 訂單管理與 Dashboard
- 使用者管理（啟用 / 停用）

建置並部署：
```bash
npm run build
```

`

---

## 功能模組

| 模組         | 說明                            | 狀態 |
|------------|-------------------------------|----|
| 使用者模組      | 註冊 / 登入 / 登出（JWT）             | ✅  |
| 商品模組       | 商品 CRUD、分類查詢                  | ✅  |
| 分類與標籤      | 查詢開放，建立/刪除需 ADMIN             | ✅  |
| 訂單模組       | 建立訂單、查詢訂單                     | ✅  |
| 管理端模組      | 權限控管 + Dashboard API          | ✅  |
| Swagger 文件 | 自動產生 API 文件於 `/swagger-ui.htm | ✅  |

---

## 授權

此為個人作品集專案，僅供學習展示用途。
