# Spring Boot Backend RESTful API

pet-shop-backend-rest-api 是本系統的後端專案，使用 Spring Boot 3 建構，提供完整的 RESTful API 給前端使用（前端專案見：[pet-shop-vue-frontend](https://github.com/lichenghsu/pet-shop-vue-frontend)）。

本專案重構自[轉職班專題](https://github.com/lichenghsu/CGA104G1-pet-shop)，採用前後端分離架構，並使用現代化技術堆疊，作為電商平台的後端核心。

---

## 技術堆疊

* Spring Boot 3
* Spring Security + JWT
* JPA / Hibernate
* MySQL / PostgreSQL
* Redis 快取
* Lombok
* Maven
* SpringDoc OpenAPI (Swagger)
* Jib（打包用，取代 Dockerfile）

---

## 專案結構

```text
src/
├── config/         # Security、CORS 等設定
├── controller/     # REST API 控制器
├── dto/            # 請求與回應封裝物件
├── entity/         # JPA Entity 模型
├── repository/     # 資料存取介面
├── security/       # JWT、使用者認證相關
├── service/        # 商業邏輯處理
├── exception/      # 自訂例外處理
└── PetShopApplication.java
```

---

## 系統架構與部署目標

本專案將搭配前端 `pet-shop-vue-frontend` 一起部署為完整的 WebApp。

後端將以 Spring Boot 打包為獨立容器，前端將打包為靜態頁面，可部屬至 GitHub Pages 或 Vercel，搭配前後端分離架構進行部署。

---

## 使用 Podman 啟動後端服務

本專案使用 [Podman](https://podman.io/) 作為容器執行環境，以提供無 daemon、rootless 更安全的開發流程。

### 安裝 Podman

#### Ubuntu / Debian

```bash
sudo apt update
sudo apt install podman podman-compose
```

#### macOS（使用 Homebrew）

```bash
brew install podman podman-compose
brew install podman-desktop
```

---

## 建立並啟動容器服務

```bash
podman-compose up -d
```

會啟動以下容器：

* `mysql`：資料庫服務，port `3306`
* `redis`：快取服務，port `6379`
* `adminer`：資料庫圖形介面，port `8081`

---

## 停止與清除容器

```bash
podman-compose down
```

---

## 快速啟動 Spring Boot 應用

```bash
# 使用 dev 環境
./mvnw spring-boot:run -Dspring-boot.run.profiles=dev
```

```bash
# 使用 prod 環境
./mvnw spring-boot:run -Dspring-boot.run.profiles=prod
```

或設定環境變數：

```bash
export SPRING_PROFILES_ACTIVE=prod
```

* 預設啟動埠號為：`8080`
* Swagger UI：`http://localhost:8080/swagger-ui/index.html`

---

## 資料庫測試連線（MySQL）

```bash
mysql -h 127.0.0.1 -P 3306 -u root -p
```

---

## 重要資源與結構

```bash
application.yml            # 指定啟用的 Profile
application-dev.yml        # 開發環境設定
application-prod.yml       # 正式環境設定
```

---

## 功能模組

| 模組         | 說明                              | 狀態 |
| ---------- | ------------------------------- | -- |
| 使用者模組      | 註冊 / 登入 / 登出（JWT）               | ✅  |
| 商品模組       | 商品 CRUD、分類查詢                    | ✅  |
| 評論模組       | 新增 / 查詢 / 刪除（需 ADMIN）           | ✅  |
| 分類與標籤      | 查詢開放，建立/刪除需 ADMIN               | ✅  |
| 訂單模組       | 建立訂單、查詢訂單                       | ✅  |
| 管理端模組      | 權限控管 + Dashboard API            | ⏳  |
| Swagger 文件 | 自動產生 API 文件於 `/swagger-ui.html` | ✅  |

---

## 部署整合說明（含前端）

1. 使用 `Jib` 打包 Spring Boot 為 container image

```bash
./mvnw compile jib:dockerBuild
```

2. 將前端專案（Vue）打包後部署至 GitHub Pages 或 Vercel

```bash
npm run build
```

3. 後端部署建議平台：Railway、Render、Fly.io

---

## TODO

* [x] Review 模組串接與權限管理
* [x] SecurityConfig 加入評論、分類、標籤安全規則
* [x] README 整合為可部署 WebApp 說明文件
* [ ] Dashboard 前端 Vue 專案實作與整合
* [ ] Render 或 Railway 後端自動部署腳本

---

## 授權

此為個人作品集專案，僅供學習展示用途。
