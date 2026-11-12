# ĐẶC TẢ NGHIỆP VỤ WEB CÁ NHÂN (SPECIFICATIONS)
> *Dùng làm ngữ cảnh (Context) gửi cho AI để sinh Test Plan & Test Case chính xác theo dự án riêng của bạn.*
> *Nguồn: `docs/SRS.md`, mã nguồn `backend/` và `frontend/`, đồ thị mã nguồn graphify (`DEVCLIMB/graphify-out`). Khi SRS và mã nguồn khác nhau, tài liệu này mô tả theo mã nguồn và ghi điểm lệch ở mục 7.*

---

## 1. THÔNG TIN DỰ ÁN
* **Tên đề tài:** DevClimb - Hệ thống viết code và chấm code trực tuyến bằng Judge0.
* **Công nghệ:**
  * Backend: Java 21, Jakarta Servlet 6.0 (WAR trên Apache Tomcat 11), Hibernate/JPA, MySQL 8, Maven.
  * Frontend: HTML/CSS/JavaScript thuần, CodeMirror, GSAP, Chart.js.
  * Dịch vụ ngoài: Firebase Authentication, Judge0 CE, Cloudflare R2.
* **URL chạy local:**
  * Backend API: `http://localhost:8080/devclimb-backend/api/v1` (theo `frontend/utils/local-config.js`).
  * Frontend: phục vụ thư mục gốc repo ở cổng 5500, mở `http://localhost:5500/frontend/index.html`.
* **Kiến trúc:** 30 servlet controller và filter (`@WebServlet`, `@WebFilter`) gọi DAO, DAO dùng JPA entity. Không có tầng service riêng cho nghiệp vụ.

## 2. VAI TRÒ VÀ YÊU CẦU (theo `docs/SRS.md`)
| Vai trò | Chức năng |
| :--- | :--- |
| Guest | Đăng ký, đăng nhập, xem và tìm kiếm bài tập (FR01–FR06) |
| User | Viết code, chạy thử, nộp bài, xem kết quả, xem bảng xếp hạng, tạo nhóm (FR07–FR11) |
| Admin | Quản lý bài tập, testcase, người dùng (FR12–FR16) |

| ID | Yêu cầu chức năng | Ưu tiên | Mục đặc tả |
| :--- | :--- | :---: | :--- |
| FR01 | Tạo tài khoản mới | Cao | 4.1 |
| FR02 | Đăng nhập | Cao | 4.2, 4.3 |
| FR03 | Hiển thị danh sách bài tập | Cao | 4.5 |
| FR04 | Tìm kiếm bài tập theo từ khoá | Trung bình | 4.5 |
| FR05 | Lọc bài tập theo độ khó | Trung bình | 4.5 |
| FR06 | Xem chi tiết bài tập | Cao | 4.5 |
| FR07 | Viết code trên website | Cao | 4.6 |
| FR08 | Thực thi code thử nghiệm | Cao | 4.7 |
| FR09 | Nộp bài, nhận kết quả tự động | Cao | 4.6 |
| FR10 | Xem bảng xếp hạng người dùng | Trung bình | 4.8 |
| FR11 | Tạo nhóm thi đấu | Trung bình | 4.9 |
| FR12–FR14 | Thêm, sửa, xóa bài tập | Cao / Trung bình | 4.10 |
| FR15 | Quản lý testcase | Cao | 4.11 |
| FR16 | Quản lý tài khoản người dùng | Cao | 4.12 |

* **UC01 - Đăng nhập:** nhập email và mật khẩu → Firebase xác thực → xác định vai trò → chuyển đến trang tương ứng. Luồng thay thế: sai mật khẩu, tài khoản không tồn tại, lỗi kết nối dịch vụ xác thực.
* **UC02 - Nộp bài:** chọn bài → viết code → nộp → Judge0 chạy với các test case → hiển thị kết quả. Luồng thay thế: Compile Error, Runtime Error, Time Limit Exceeded, Wrong Answer, lỗi kết nối Judge0.
* **UC03 - Quản lý bài tập:** Admin thêm, sửa, xóa bài tập và lưu vào hệ thống.

## 3. MÀN HÌNH VÀ API

### 3.1 Màn hình chính
| Màn hình | Đường dẫn | Vai trò |
| :--- | :--- | :--- |
| Trang chủ khách | `/frontend/index.html` | Guest |
| Đăng nhập | `/frontend/pages/guest/auth/login.html` | Guest |
| Đăng ký | `/frontend/pages/guest/auth/register.html` | Guest |
| Quên mật khẩu | `/frontend/pages/guest/auth/forgot-password.html` | Guest |
| Danh sách bài tập | `/frontend/pages/guest/puzzle/index.html`, `/frontend/pages/user/puzzle/index.html` | Guest / User |
| Giải bài (editor + chấm) | `/frontend/pages/user/solve/index.html` | User |
| Trang chủ người dùng | `/frontend/pages/user/home/index.html` | User |
| Tìm người dùng | `/frontend/pages/user/user-search/index.html` | User |
| Nhóm, tìm nhóm, tạo nhóm | `/frontend/pages/user/team/index.html`, `.../team-search/`, `.../team-create/` | User |
| Bảng điều khiển Admin | `/frontend/pages/admin/dashboard/index.html` | Admin |

### 3.2 API trong phạm vi
| Endpoint | Phương thức | Controller | Mô tả |
| :--- | :---: | :--- | :--- |
| `/auth/login`, `/auth/register` | POST | `AuthController` | Đăng nhập, đăng ký bằng `idToken` Firebase |
| `/auth/logout` | POST | `AuthController` | Huỷ session |
| `/auth/me` | GET | `AuthController` | Thông tin phiên hiện tại |
| `/puzzles` | GET | `PuzzleController` | Danh sách, tìm kiếm, lọc, chi tiết bài tập |
| `/submit` | POST | `SubmissionController` | Nộp code, chấm bằng Judge0 |
| `/users` | GET | `UserSearchController` | Tìm người dùng, kèm điểm |
| `/teams`, `/teams/stats` | GET | `TeamBrowseController` | Danh sách và thống kê nhóm |
| `/teams/create`, `/join`, `/leave`, `/kick` | POST | `TeamManageController` | Quản lý thành viên nhóm |
| `/teams/update`, `/shoutout`, `/transfer` | PUT | `TeamManageController` | Sửa nhóm, chuyển chủ nhóm |
| `/teams/disband` | DELETE | `TeamManageController` | Giải tán nhóm |
| `/admin/puzzles`, `/admin/puzzle` | GET, POST, PUT, DELETE | `AdminPuzzleController` | CRUD bài tập |
| `/admin/testcases`, `/admin/testcase` | GET, POST, PUT, DELETE | `AdminTestcaseController` | CRUD testcase |
| `/admin/users`, `/admin/user` | GET, DELETE | `AdminUserController` | Danh sách, chi tiết, vô hiệu hoá người dùng |
| `/admin/user/ban`, `/admin/user/reactivate` | PUT | `AdminUserController` | Cấm, mở lại tài khoản |

Tất cả đường dẫn tính từ `/api/v1`. Mọi API `/admin/*` đi qua `AuthFilter`.

## 4. QUY TẮC NGHIỆP VỤ (BUSINESS RULES)

### 4.1 Đăng ký (FR01)
* Form: `#regUsername`, `#regEmail`, `#regPassword`, `#regConfirm`; lỗi hiển thị ở `#registerError`.
* `password` khác `confirmPassword` → **"Passwords do not match"** (kiểm tra ở frontend, không gọi Firebase).
* Firebase kiểm tra email và mật khẩu: email đã tồn tại → **"Email already registered"**; mật khẩu yếu (dưới 6 ký tự) → **"Password is too weak"**.
* Backend: thiếu `idToken` → 400 `idToken is required`; token sai → 401 `Invalid Firebase token`; user đã tồn tại → 409 `User already exists`; `username` trùng → 409 `Username already taken`.
* `username` rỗng → hệ thống tự sinh tên.
* Thành công → chuyển đến `/frontend/pages/user/home/index.html`.

### 4.2 Đăng nhập (FR02)
* Form: `#email`, `#password`; nút "Authenticate"; lỗi hiển thị ở `#loginError`.
* Đúng → chuyển đến trang chủ user; sai email hoặc mật khẩu → **"Wrong email or password"**.
* Backend: thiếu `idToken` → 400; token sai → 401 `Invalid Firebase token`; không có user trong DB → 404 `User not found. Please register first.`; tài khoản bị cấm → 403 `Account is banned` hoặc `Account banned due to expired warning`.
* `/auth/me` khi chưa đăng nhập → 401 `Not authenticated`.
* Trang Admin gọi `checkAdminAuth()`: chưa đăng nhập hoặc không phải Admin → chuyển về trang chủ user.

### 4.3 Đăng xuất và Quên mật khẩu (FR02)
* Đăng xuất: frontend gọi Firebase `signOut` rồi `POST /auth/logout`; backend huỷ session và trả `Logged out`, kể cả khi chưa đăng nhập. Sau đó `/auth/me` trả 401.
* Quên mật khẩu (`#resetEmail`, lỗi ở `#forgotError`): email rỗng → **"Please enter your email"**. Có email → luôn hiển thị **"We sent a password reset link to <email>"**, dù email có tồn tại hay không (chống dò tài khoản). Không có API backend; Firebase gửi email.

### 4.4 Phiên làm việc
* Đăng nhập tạo session phía server; frontend gửi cookie session kèm mọi request.
* Frontend dùng cùng hostname với trang (`localhost`) để cookie được gửi; Google/GitHub chỉ chạy trên `localhost`, không chạy trên `127.0.0.1`.

### 4.5 Danh sách, tìm kiếm, lọc bài tập (FR03–FR06)
* Tham số: `id`, `search`, `difficulty` (Easy / Medium / Hard), `language`, `page`, `limit`.
* `page`: mặc định 1; nhỏ hơn 1 → 1; không phải số → 1.
* `limit`: mặc định 10; nhỏ hơn 1 → 1; lớn hơn 100 → 100; không phải số → 10.
* Chi tiết: `id` không phải số → 400 `Invalid id format`; không tồn tại → 404 `Puzzle not found`.

### 4.6 Viết code và Nộp bài (FR07, FR09)
* Trang giải bài có editor CodeMirror và một nút `#submitBtn` ("Submit"); khi đang chấm hiển thị "Running".
* Body: `puz_id`, `lang_id`, `user_code`, `function_name`, `prog_time`, `prog_code`.
* `lang_id` ánh xạ sang Judge0: 1 → Python (71), 2 → JavaScript (63), 3 → PHP (68). Giá trị khác → mặc định Python.
* Chưa đăng nhập → 401 `Not authenticated`.
* Hai lần nộp của cùng user cách nhau dưới 2000 ms → 429 `Please wait before submitting again`.
* `user_code` null hoặc dài hơn 100.000 ký tự → 400 `Source code too large (max 100KB)`.
* Bài tập không có test case → 404 `No test cases found for this puzzle`.
* Lỗi Judge0 → 500 `Judge0 submission failed: ...` hoặc `Judge0 polling failed: ...`.
* Kết quả: `Accepted` khi đạt toàn bộ, `Failed` khi có test case không đạt, kèm trạng thái, thời gian, bộ nhớ từng test case. Compile Error dừng chấm và hiển thị Compiler Output; Wrong Answer hiển thị input, kết quả mong đợi và kết quả thực tế.

### 4.7 Chạy thử code (FR08)
* SRS yêu cầu chạy thử code (không tính là nộp bài). Mã nguồn hiện chỉ có nút Submit và API `/submit`, không có chức năng chạy thử riêng (xem DEF-08).

### 4.8 Bảng xếp hạng và tìm người dùng (FR10)
* `GET /users`: tham số `search` (khớp một phần tên, không phân biệt hoa thường), `page`, `limit` (cùng quy tắc biên như 4.5).
* Chỉ trả user đang hoạt động (`user_isactive = true`); mỗi dòng có `displayName`, `avatar`, `isAdmin`, `groupName`, `totalScore`, `totalPuzzles`.
* `totalScore` là tổng điểm các bài đã giải. Danh sách sắp xếp theo tên A–Z, không theo điểm (xem DEF-09).
* `GET /teams`: tìm theo tên; `sortBy` = `members` hoặc `solved`, `order` = `asc` (mặc định giảm dần); không có `sortBy` → sắp theo mã nhóm.

### 4.9 Nhóm (FR11)
* Mọi thao tác yêu cầu đăng nhập (401 `Not authenticated`). Body không phải JSON → 400 `Invalid JSON`.
* **Tạo nhóm:** `name` 1–100 ký tự, không chỉ có khoảng trắng → nếu sai 400 `Team name must be 1-100 characters`; tên trùng → 409 `A team with this name already exists`; đang là chủ nhóm khác → 400 `You already own a team. Disband it first.` `isPublic` mặc định `true`. Đang là thành viên (không phải chủ) nhóm khác → rời nhóm cũ rồi tạo nhóm mới.
* **Tham gia:** thiếu `teamId` → 400 `teamId is required`; không phải số → 400 `Invalid teamId`; không tồn tại → 404 `Team not found`; nhóm private → 400 `This team is private. Cannot join.`; đang ở nhóm khác → 400 `Already in a team. Leave it first.`; đang là chủ nhóm → 400 `You own a team. Transfer ownership or disband first.`
* **Rời nhóm:** chưa có nhóm → 400 `Not in a team`; là chủ nhóm → 400 `You are the owner. Transfer ownership or disband the team first.`
* **Sửa nhóm / shoutout / chuyển chủ / kick / giải tán:** chỉ chủ nhóm (403 `Only the team owner can ...`). Shoutout bắt buộc, tối đa 500 ký tự (`Shoutout must be at most 500 characters`). Chuyển chủ: `newOwnerId` bắt buộc, phải là thành viên (`New owner must be a team member`). Kick: `userId` bắt buộc, không được tự kick (`Cannot kick yourself`), phải là thành viên (`User is not a member of your team`).

### 4.10 CRUD bài tập của Admin (FR12–FR14)
* Thêm (POST): `title` bắt buộc, không rỗng hoặc chỉ khoảng trắng → 400 `title is required`; `content`, `functionName`, `difficulty`, `score` (mặc định 0), `languageId`, `contestId` tuỳ chọn.
* Sửa (PUT) và xem (GET `/admin/puzzle`): thiếu `id` → 400 `id is required`; không phải số → 400 `Invalid id`; không tồn tại → 404 `Puzzle not found`.
* Xóa (DELETE): thiếu `id` → 400; không phải số → 400 `Invalid id`. Giao diện có hộp thoại xác nhận trước khi xóa.
* Phân quyền (`AuthFilter`): chưa đăng nhập → 401 `Not authenticated`; không phải Admin → 403 `Forbidden. Admin access required.`

### 4.11 Quản lý testcase (FR15)
* Danh sách (GET `/admin/testcases?puzzle=`): thiếu `puzzle` → 400 `puzzle is required`; không phải số → 400 `Invalid puzzle id`.
* Xem (GET `/admin/testcase?id=`): thiếu `id` → 400; không phải số → 400 `Invalid id`; không tồn tại → 404 `Testcase not found`.
* Thêm (POST): `puzId` bắt buộc (400 `puzId is required`); `input`, `output` không bắt buộc (xem DEF-12, DEF-13).
* Sửa (PUT): chỉ cập nhật trường có trong body; không tồn tại → 404 `Testcase not found`.
* Xóa (DELETE): thiếu `id` → 400 `id is required`; không phải số → 400 `Invalid id`.

### 4.12 Quản lý người dùng (FR16)
* Danh sách (GET `/admin/users`); chi tiết (GET `/admin/user`) theo `id`, `username` hoặc `email`: thiếu cả ba → 400 `Provide id, username, or email`; không tìm thấy → 404 `User not found`.
* Cấm (PUT `/admin/user/ban?id=`): đặt `user_isactive = false`, trả `User banned`; user đó đăng nhập → 403 `Account is banned`.
* Mở lại (PUT `/admin/user/reactivate?id=`): đặt `user_isactive = true`, trả `User reactivated`.
* Vô hiệu hoá (DELETE `/admin/user?id=`): xóa mềm, trả `User deactivated`; bản ghi vẫn còn trong DB.
* Thiếu `id` → 400 `id is required`; không phải số → 400 `Invalid id`.

## 5. DỮ LIỆU CHÍNH
| Thực thể | Trường quan trọng | Ràng buộc trong mã nguồn |
| :--- | :--- | :--- |
| User | `user_id`, `user_email`, `user_name`, `user_firebaseuid`, `user_isadmin`, `user_isactive`, `team_id` | `user_isadmin` mặc định false; `user_isactive` mặc định true; `user_name` không trùng (kiểm tra khi đăng ký) |
| Puzzle | `puz_id`, `puz_title`, `puz_content`, `puz_function`, `puz_difficulty`, `puz_score`, ngôn ngữ, cuộc thi | `puz_title` bắt buộc; `puz_score` mặc định 0 |
| Testcase | `tc_id`, `tc_input`, `tc_output` (TEXT), `puz_id` | `puz_id` bắt buộc khi tạo |
| Team | `team_id`, `team_name`, `team_ownerid`, `team_shoutout`, `team_ispublic`, `team_isactive` | Tên 1–100 ký tự, không trùng; shoutout tối đa 500 ký tự; mặc định public và active |
| Progress | `prog_id`, `user_id`, `puz_id`, `prog_date`, `prog_time`, `prog_code` | Bản ghi bài đã giải, dùng để tính điểm |

## 6. YÊU CẦU PHI CHỨC NĂNG
| ID | Yêu cầu | Cách kiểm tra |
| :--- | :--- | :--- |
| NFR01 | Phản hồi dưới 3 giây | Đo thời gian API chính và tải trang |
| NFR02 | Tương thích desktop và mobile | Kiểm tra ở độ rộng 375px và 1366px |
| NFR03 | Bảo mật thông tin đăng nhập | Mật khẩu chỉ xử lý ở Firebase; API không trả mật khẩu; API admin chặn đúng 401/403 |
| NFR04 | Ổn định khi nhiều người truy cập | Kiểm tra khói với nhiều request đồng thời (không phải load test đầy đủ) |
| NFR05 | Giao diện dễ dùng, trực quan | Đánh giá theo heuristic trên luồng chính |

Ràng buộc: chỉ tiếng Anh; không dùng được khi offline; phụ thuộc Firebase và Judge0.

## 7. ĐIỂM LỆCH GIỮA SRS VÀ MÃ NGUỒN
| Mã | Mô tả | Test Case |
| :--- | :--- | :--- |
| DEF-01 | SRS ghi Python / C / C++; mã nguồn chỉ hỗ trợ JavaScript, PHP, Python; `lang_id` lạ bị âm thầm đổi thành Python | TC_SUB_16 |
| DEF-02 | SRS (TC006) yêu cầu chặn nộp bài rỗng; backend chỉ chặn `user_code` null | TC_SUB_10 |
| DEF-03 | Thiếu `puz_id` hoặc `lang_id` gây `NullPointerException` thay vì 400 | TC_SUB_11 |
| DEF-04 | Backend không kiểm tra `difficulty` thuộc Easy/Medium/Hard | TC_CRUD_04 |
| DEF-05 | Backend không chặn `score` âm | TC_CRUD_05 |
| DEF-06 | UC01 ghi thông báo "Mật khẩu không chính xác" và "Tài khoản không tồn tại"; mã nguồn dùng chung "Wrong email or password" | Lệch tài liệu, không có ca Fail riêng |
| DEF-07 | UC01 yêu cầu chuyển trang theo vai trò; form đăng nhập luôn chuyển về trang chủ user | TC_AUTH_10 |
| DEF-08 | FR08 yêu cầu chạy thử code; không có chức năng chạy thử riêng | TC_RUN_01 |
| DEF-09 | FR10 yêu cầu xếp hạng theo điểm; `/users` sắp theo tên A–Z | TC_RANK_02 |
| DEF-10 | Tạo nhóm: thành viên bị rời nhóm cũ trước khi kiểm tra trùng tên, nên khi trả 409 thì đã mất nhóm cũ | TC_TEAM_07 |
| DEF-11 | Quên mật khẩu: bỏ trống email thì nút gửi bị khoá luôn, không bật lại | TC_FPW_04 |
| DEF-12 | Tạo testcase không kiểm tra `puzId` có tồn tại | TC_TCM_06 |
| DEF-13 | Tạo testcase không bắt buộc `input` và `output` | TC_TCM_07 |
| DEF-14 | Cấm người dùng với `id` không tồn tại vẫn trả thành công | TC_USR_08 |

## 8. ĐIỀU KIỆN CHẠY LOCAL
Java 21 trở lên, Tomcat 11, MySQL và các file cấu hình cục bộ không đưa lên Git: `persistence.xml`, `db.properties`, `firebase-service-account.json`, `r2.properties`, `ca-truststore.jks` (trong `resources/META-INF` và `backend/`), `frontend/utils/constants.js`.
