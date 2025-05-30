# Spring Boot Backend RESTful API

pet-shop-backend-rest-api 是本系統的後端專案，使用 Spring Boot 3 建構，提供完整的 RESTful API 給前端使用（前端專案見：[pet-shop-vue-frontend](<https://github.com/lichenghsu/pet-shop-vue-frontend>)）。

本專案重構自[轉職班專題](<https://github.com/lichenghsu/CGA104G1-pet-shop>)，採用前後端分離架構，並使用現代化技術堆疊，作為電商平台的後端核心。

---

## 技術堆疊

- Spring Boot 3
- Spring Security + JWT
- JPA / Hibernate
- MySQL / PostgreSQL
- Lombok
- Maven
- SpringDoc OpenAPI (Swagger)
- Jib（打包用，取代 Dockerfile）

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
本專案使用 [Podman](https://podman.io/) 作為容器執行環境，以提供無 daemon、rootless 更安全的開發流程。

## 安裝 Podman

### Ubuntu / Debian

```bash
sudo apt update
sudo apt install podman podman-compose
```

### macOS（使用 Homebrew）

```bash
brew install podman podman-compose
brew install podman-desktop
```

---

## 使用 Podman 啟動服務

```bash
podman-compose up -d
```

啟動以下容器：

- `mysql`：資料庫服務，port `3306`
- `redis`：快取服務，port `6379`
- `adminer`：資料庫圖形介面，port `8081`

---

## 停止與清除

```bash
podman-compose down
```

---

## 測試連線（MySQL）

```bash
mysql -h 127.0.0.1 -P 3306 -u root -p
```

---

## 注意事項

- 若出現網路綁定問題，請檢查 rootless 模式是否支援 `localhost` port 映射
- 可使用 `podman ps` 與 `podman logs` 檢查容器狀態
- 若需兼容 Docker 指令，可設定：`alias docker=podman`

---

## 快速啟動

```bash
# 請先設定資料庫連線資訊於 application.yml
./mvnw spring-boot:run
```

- 目前設定的資料庫為：`____`
- 目前使用的埠號為：`8080`

---

## 功能模組

| 模組         | 說明                              | 狀態 |
|------------|---------------------------------|----|
| 使用者模組      | 註冊 / 登入 / 登出（JWT）               | ✅  |
| 商品模組       | 商品 CRUD、分類查詢                    | 🔧 |
| 訂單模組       | 建立訂單、查詢訂單                       | 🔧 |
| 管理端模組      | 權限控管 + 商品/會員管理                  | ⏳  |
| Swagger 文件 | 自動產生 API 文件於 `/swagger-ui.html` | ✅  |

---

## 測試與部署

- 單元測試（JUnit5 + Mockito）
- 使用 Jib 打包容器：`mvn compile jib:dockerBuild`
- 推薦部署平台：Railway / Render

---

## TODO

- [x] 初始化專案骨架
- [ ] 設計 `User` 與 `Role` Entity
- [ ] 完成 JWT 登入/註冊流程
- [ ] 商品模組 API 設計
- [ ] 整合 Swagger
- [ ] Railway 自動部署

---

## 授權

此為個人作品集專案，僅供學習展示用途。
