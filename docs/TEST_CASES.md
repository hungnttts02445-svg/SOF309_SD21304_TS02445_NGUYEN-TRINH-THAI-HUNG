# BỘ KỊCH BẢN KIỂM THỬ (TEST CASES)

> Thiết kế từ `docs/SPECIFICATIONS.md`; truy vết theo `docs/TEST_PLAN.md` mục 18.
> **Ưu tiên:** Cao / TB (trung bình) / Thấp. **Cấp độ:** Unit (JUnit cho controller), UI (Selenium), Manual (thủ công).
> **Kỹ thuật:** EP (phân vùng tương đương), BVA (giá trị biên), DT (bảng quyết định), ST (chuyển trạng thái), EG (đoán lỗi), UC (luồng use case).
> Trạng thái ban đầu là **Chưa chạy**. Ca gắn **DEF-xx** dự kiến Fail cho đến khi mã nguồn được sửa (xem `SPECIFICATIONS.md` mục 7).

## 1. Đăng ký (FR01)

| Test Case ID | Mã YC | Chức năng | Mô tả ca kiểm thử | Loại test (Pos/Neg) | Ưu tiên | Cấp độ | Kỹ thuật | Tiền điều kiện | Các bước thực hiện | Dữ liệu kiểm thử (Test Data) | Kết quả mong đợi | Kết quả thực tế | Trạng thái (Pass/Fail) |
| :---: | :---: | :---: | :--- | :---: | :---: | :---: | :---: | :--- | :--- | :--- | :--- | :---: | :---: |
| **TC_REG_01** | FR01 | Register | Đăng ký thành công | Positive | Cao | UI | EP | Email chưa đăng ký | 1. Mở trang đăng ký<br>2. Nhập đủ thông tin<br>3. Bấm đăng ký | Username, email mới; `Pass@1234` ở cả 2 ô | Chuyển đến trang chủ user | — | Chưa chạy |
| **TC_REG_02** | FR01 | Register | Mật khẩu xác nhận không khớp | Negative | Cao | UI | EP | Mở trang đăng ký | 1. Nhập 2 mật khẩu khác nhau<br>2. Bấm đăng ký | `Pass@1234` / `Pass@9999` | Hiển thị "Passwords do not match" | — | Chưa chạy |
| **TC_REG_03** | FR01 | Register | Email đã được đăng ký | Negative | Cao | UI | EP | Có tài khoản với email này | 1. Nhập email đã tồn tại<br>2. Bấm đăng ký | Email đã có | Hiển thị "Email already registered" | — | Chưa chạy |
| **TC_REG_04** | FR01 | Register | Mật khẩu dưới biên | Negative | TB | UI | BVA | Mở trang đăng ký | 1. Nhập mật khẩu 5 ký tự<br>2. Bấm đăng ký | `12345` | Hiển thị "Password is too weak" | — | Chưa chạy |
| **TC_REG_05** | FR01 | Register | Mật khẩu đúng biên | Positive | TB | UI | BVA | Email chưa đăng ký | 1. Nhập mật khẩu 6 ký tự<br>2. Bấm đăng ký | `123456`, email mới | Đăng ký thành công | — | Chưa chạy |
| **TC_REG_06** | FR01 | Register API | Thiếu `idToken` | Negative | Cao | Unit | EG | Không có | Gọi `POST /auth/register` không có `idToken` | `{}` | 400 `idToken is required` | — | Chưa chạy |
| **TC_REG_07** | FR01 | Register API | Token không hợp lệ | Negative | Cao | Unit | EP | Không có | Gọi API với token sai | `idToken=invalid` | 401 `Invalid Firebase token` | — | Chưa chạy |
| **TC_REG_08** | FR01 | Register API | User đã tồn tại | Negative | Cao | Unit | EP | User đã có trong DB | Gọi API với token của user đã có | Token hợp lệ | 409 `User already exists` | — | Chưa chạy |
| **TC_REG_09** | FR01 | Register API | Username đã được dùng | Negative | Cao | Unit | EP | Username đã có trong DB | Gọi API với user mới, username trùng | Username đã có | 409 `Username already taken` | — | Chưa chạy |

## 2. Đăng nhập, Đăng xuất, Quên mật khẩu (FR02)

| Test Case ID | Mã YC | Chức năng | Mô tả ca kiểm thử | Loại test (Pos/Neg) | Ưu tiên | Cấp độ | Kỹ thuật | Tiền điều kiện | Các bước thực hiện | Dữ liệu kiểm thử (Test Data) | Kết quả mong đợi | Kết quả thực tế | Trạng thái (Pass/Fail) |
| :---: | :---: | :---: | :--- | :---: | :---: | :---: | :---: | :--- | :--- | :--- | :--- | :---: | :---: |
| **TC_AUTH_01** | FR02, UC01 | Login | Đăng nhập thành công | Positive | Cao | UI | UC | Có tài khoản User | 1. Nhập email<br>2. Nhập mật khẩu<br>3. Bấm Authenticate | Tài khoản User hợp lệ | Chuyển đến trang chủ user | — | Chưa chạy |
| **TC_AUTH_02** | FR02, UC01 | Login | Sai mật khẩu | Negative | Cao | UI | EP | Có tài khoản User | 1. Nhập email đúng, mật khẩu sai<br>2. Bấm Authenticate | Mật khẩu sai | Hiển thị "Wrong email or password" | — | Chưa chạy |
| **TC_AUTH_03** | FR02 | Login | Bỏ trống mật khẩu | Negative | Cao | UI | EP | Mở trang đăng nhập | 1. Nhập email, bỏ trống mật khẩu<br>2. Bấm Authenticate | `""` | Hiển thị lỗi, không chuyển trang | — | Chưa chạy |
| **TC_AUTH_04** | FR02 | Login API | Thiếu `idToken` | Negative | Cao | Unit | EG | Không có | Gọi `POST /auth/login` không có `idToken` | `{}` | 400 `idToken is required` | — | Chưa chạy |
| **TC_AUTH_05** | FR02 | Login API | Token không hợp lệ | Negative | Cao | Unit | EP | Không có | Gọi API với token sai | `idToken=invalid` | 401 `Invalid Firebase token` | — | Chưa chạy |
| **TC_AUTH_06** | FR02 | Login API | Có Firebase nhưng chưa có trong DB | Negative | TB | Unit | EP | User có ở Firebase, chưa có trong DB | Gọi API với token của user đó | Token hợp lệ | 404 `User not found. Please register first.` | — | Chưa chạy |
| **TC_AUTH_07** | FR02, FR16 | Login API | Tài khoản bị cấm | Negative | Cao | Unit | ST | Tài khoản đã bị cấm | Đăng nhập bằng tài khoản bị cấm | Tài khoản bị cấm | 403 `Account is banned` | — | Chưa chạy |
| **TC_AUTH_08** | FR02 | Session | Lấy phiên khi chưa đăng nhập | Negative | Cao | Unit | EP | Không có session | Gọi `GET /auth/me` | Không có | 401 `Not authenticated` | — | Chưa chạy |
| **TC_AUTH_09** | FR02, UC01 | Login | Email chưa có tài khoản | Negative | TB | UI | UC | Email chưa đăng ký | 1. Nhập email chưa đăng ký<br>2. Bấm Authenticate | Email không tồn tại | Hiển thị "Wrong email or password", không chuyển trang | — | Chưa chạy |
| **TC_AUTH_10** | FR02, UC01 | Login | Admin đăng nhập, chuyển trang theo vai trò | Positive | TB | UI | UC | Có tài khoản Admin | Đăng nhập bằng Admin | Tài khoản Admin | Vào trang Admin. **DEF-07**, dự kiến Fail | — | Chưa chạy |
| **TC_AUTH_11** | FR02 | Logout | Đăng xuất thành công | Positive | Cao | UI | ST | Đã đăng nhập | 1. Bấm đăng xuất<br>2. Gọi `/auth/me` | Không có | Trả `Logged out`; sau đó `/auth/me` trả 401 | — | Chưa chạy |
| **TC_AUTH_12** | FR02 | Logout API | Đăng xuất khi chưa đăng nhập | Positive | Thấp | Unit | EG | Không có session | Gọi `POST /auth/logout` | Không có | 200 `Logged out`, không lỗi | — | Chưa chạy |
| **TC_FPW_01** | FR02 | Forgot password | Gửi yêu cầu với email đã đăng ký | Positive | TB | UI | EP | Có tài khoản | 1. Mở trang quên mật khẩu<br>2. Nhập email<br>3. Bấm gửi | Email đã đăng ký | Hiển thị "We sent a password reset link to <email>" | — | Chưa chạy |
| **TC_FPW_02** | FR02, NFR03 | Forgot password | Email chưa đăng ký | Positive | TB | UI | EP | Không có | Nhập email chưa đăng ký, bấm gửi | Email không tồn tại | Hiển thị cùng thông báo như TC_FPW_01 (không lộ email có tồn tại hay không) | — | Chưa chạy |
| **TC_FPW_03** | FR02 | Forgot password | Bỏ trống email | Negative | TB | UI | EP | Mở trang quên mật khẩu | Bỏ trống email, bấm gửi | `""` | Hiển thị "Please enter your email" | — | Chưa chạy |
| **TC_FPW_04** | FR02 | Forgot password | Gửi lại sau khi bỏ trống email | Negative | Thấp | UI | EG | Đã chạy TC_FPW_03 | Nhập email hợp lệ, bấm gửi lần nữa | Email hợp lệ | Nút gửi hoạt động lại và gửi được. **DEF-11**, dự kiến Fail | — | Chưa chạy |

## 3. Bài tập (FR03–FR06)

| Test Case ID | Mã YC | Chức năng | Mô tả ca kiểm thử | Loại test (Pos/Neg) | Ưu tiên | Cấp độ | Kỹ thuật | Tiền điều kiện | Các bước thực hiện | Dữ liệu kiểm thử (Test Data) | Kết quả mong đợi | Kết quả thực tế | Trạng thái (Pass/Fail) |
| :---: | :---: | :---: | :--- | :---: | :---: | :---: | :---: | :--- | :--- | :--- | :--- | :---: | :---: |
| **TC_PUZ_01** | FR03 | Puzzle | Danh sách mặc định | Positive | Cao | UI | EP | Có ít nhất 1 bài | Mở trang danh sách bài tập | Không tham số | Trang 1, tối đa 10 bài | — | Chưa chạy |
| **TC_PUZ_02** | FR05 | Puzzle | Lọc theo độ khó | Positive | TB | Unit | EP | Có bài ở cả 3 độ khó | Gọi với `difficulty` | `Easy`, `Medium`, `Hard` | Chỉ trả bài đúng độ khó | — | Chưa chạy |
| **TC_PUZ_03** | FR04 | Puzzle | Tìm theo từ khoá | Positive | TB | Unit | EP | Có bài chứa từ khoá | Gọi với `search` | Từ khoá có trong tên bài | Trả bài khớp | — | Chưa chạy |
| **TC_PUZ_04** | FR03 | Puzzle | `limit` dưới biên | Negative | TB | Unit | BVA | Không có | Gọi với `limit=0` | `0` | Áp dụng `limit=1` | — | Chưa chạy |
| **TC_PUZ_05** | FR03 | Puzzle | `limit` biên dưới | Positive | TB | Unit | BVA | Không có | Gọi với `limit=1` | `1` | Tối đa 1 bài | — | Chưa chạy |
| **TC_PUZ_06** | FR03 | Puzzle | `limit` biên trên | Positive | TB | Unit | BVA | Không có | Gọi với `limit=100` | `100` | Tối đa 100 bài | — | Chưa chạy |
| **TC_PUZ_07** | FR03 | Puzzle | `limit` vượt biên | Negative | TB | Unit | BVA | Không có | Gọi với `limit=101` | `101` | Áp dụng `limit=100` | — | Chưa chạy |
| **TC_PUZ_08** | FR03 | Puzzle | `page` dưới biên | Negative | TB | Unit | BVA | Không có | Gọi với `page=0` | `0` | Áp dụng `page=1` | — | Chưa chạy |
| **TC_PUZ_09** | FR03 | Puzzle | `page`, `limit` không phải số | Negative | Thấp | Unit | EG | Không có | Gọi với giá trị chữ | `page=abc&limit=xyz` | Dùng mặc định 1 và 10 | — | Chưa chạy |
| **TC_PUZ_10** | FR06 | Puzzle | Chi tiết bài tồn tại | Positive | Cao | UI | EP | Có bài tập | Mở chi tiết một bài | `id` có trong DB | Hiển thị đề bài | — | Chưa chạy |
| **TC_PUZ_11** | FR06 | Puzzle | `id` không phải số | Negative | TB | Unit | EP | Không có | Gọi với `id=abc` | `abc` | 400 `Invalid id format` | — | Chưa chạy |
| **TC_PUZ_12** | FR06 | Puzzle | `id` không tồn tại | Negative | TB | Unit | EP | Không có | Gọi với `id=999999` | `999999` | 404 `Puzzle not found` | — | Chưa chạy |

## 4. Viết code, Nộp bài, Chạy thử (FR07–FR09)

| Test Case ID | Mã YC | Chức năng | Mô tả ca kiểm thử | Loại test (Pos/Neg) | Ưu tiên | Cấp độ | Kỹ thuật | Tiền điều kiện | Các bước thực hiện | Dữ liệu kiểm thử (Test Data) | Kết quả mong đợi | Kết quả thực tế | Trạng thái (Pass/Fail) |
| :---: | :---: | :---: | :--- | :---: | :---: | :---: | :---: | :--- | :--- | :--- | :--- | :---: | :---: |
| **TC_SUB_01** | FR07, FR09, UC02 | Submit | Nộp code đúng | Positive | Cao | UI | UC | Đã đăng nhập; bài có test case | 1. Mở bài<br>2. Viết code đúng<br>3. Bấm Submit | Code đúng | Hiển thị `Accepted`, đạt mọi test case | — | Chưa chạy |
| **TC_SUB_02** | FR09, UC02 | Submit | Wrong Answer | Negative | Cao | UI | UC | Như TC_SUB_01 | Nộp code cho kết quả sai | Code sai | Hiển thị `Failed`, kèm input, kết quả mong đợi và thực tế | — | Chưa chạy |
| **TC_SUB_03** | FR09 | Submit | Chưa đăng nhập | Negative | Cao | Unit | EP | Không có session | Gọi `POST /submit` | Body hợp lệ | 401 `Not authenticated` | — | Chưa chạy |
| **TC_SUB_04** | FR09 | Submit | Nộp dưới 2000 ms | Negative | TB | Unit | BVA | Đã đăng nhập | Nộp 2 lần liên tiếp | Cách nhau dưới 2000 ms | Lần 2 trả 429 `Please wait before submitting again` | — | Chưa chạy |
| **TC_SUB_05** | FR09 | Submit | Nộp từ 2000 ms | Positive | TB | Unit | BVA | Đã đăng nhập | Nộp 2 lần cách nhau đủ | Cách nhau từ 2000 ms | Lần 2 không bị 429 | — | Chưa chạy |
| **TC_SUB_06** | FR07 | Submit | Code đúng biên 100.000 ký tự | Positive | TB | Unit | BVA | Đã đăng nhập | Nộp code dài đúng biên | 100.000 ký tự | Không bị lỗi kích thước | — | Chưa chạy |
| **TC_SUB_07** | FR07 | Submit | Code vượt biên 100.001 ký tự | Negative | TB | Unit | BVA | Đã đăng nhập | Nộp code vượt biên | 100.001 ký tự | 400 `Source code too large (max 100KB)` | — | Chưa chạy |
| **TC_SUB_08** | FR09 | Submit | `user_code` null | Negative | TB | Unit | EG | Đã đăng nhập | Gọi API không có `user_code` | Không có trường | 400 | — | Chưa chạy |
| **TC_SUB_09** | FR09 | Submit | Bài không có test case | Negative | TB | Unit | EP | Bài chưa có test case | Nộp cho bài đó | `puz_id` tương ứng | 404 `No test cases found for this puzzle` | — | Chưa chạy |
| **TC_SUB_10** | FR09 | Submit | Nộp code rỗng (SRS TC006) | Negative | TB | Unit | EP | Đã đăng nhập | Nộp `user_code` rỗng | `""` | Không cho nộp. **DEF-02**, dự kiến Fail | — | Chưa chạy |
| **TC_SUB_11** | FR09 | Submit | Thiếu `puz_id` | Negative | TB | Unit | EG | Đã đăng nhập | Gọi API không có `puz_id` | Không có trường | 400. **DEF-03**, dự kiến Fail | — | Chưa chạy |
| **TC_SUB_12** | FR07, UC02 | Submit | Code lỗi cú pháp | Negative | TB | UI | UC | Như TC_SUB_01 | Nộp code sai cú pháp | Code Python thiếu dấu `:` | Hiển thị `Failed` hoặc `Error` kèm thông tin lỗi | — | Chưa chạy |
| **TC_SUB_13** | FR09, UC02 | Submit | Runtime Error | Negative | TB | UI | UC | Như TC_SUB_01 | Nộp code gây lỗi khi chạy | Code chia cho 0 | Test case không đạt, trạng thái Runtime Error | — | Chưa chạy |
| **TC_SUB_14** | FR09, UC02 | Submit | Time Limit Exceeded | Negative | TB | UI | UC | Như TC_SUB_01 | Nộp code lặp vô hạn | `while True: pass` | Test case không đạt, trạng thái Time Limit Exceeded | — | Chưa chạy |
| **TC_SUB_15** | FR09, UC02 | Submit | Lỗi kết nối Judge0 | Negative | Thấp | Unit | UC | Judge0 không truy cập được (giả lập) | Nộp bài | Judge0 lỗi | 500 `Judge0 submission failed: ...` | — | Chưa chạy |
| **TC_SUB_16** | FR09 | Submit | Nộp bằng C++ (theo SRS) | Negative | TB | Unit | EP | Đã đăng nhập | Nộp với `lang_id` không có trong bảng ánh xạ | `lang_id=4`, code C++ | Được chấm bằng C++ hoặc báo không hỗ trợ. **DEF-01**, dự kiến Fail | — | Chưa chạy |
| **TC_RUN_01** | FR08 | Run | Chạy thử code không tính điểm | Positive | TB | UI | UC | Đã đăng nhập, mở trang giải bài | Tìm và bấm nút chạy thử | Code bất kỳ | Có kết quả chạy, không ghi nhận là nộp bài. **DEF-08**, dự kiến Fail | — | Chưa chạy |

## 5. Bảng xếp hạng, Tìm người dùng (FR10)

| Test Case ID | Mã YC | Chức năng | Mô tả ca kiểm thử | Loại test (Pos/Neg) | Ưu tiên | Cấp độ | Kỹ thuật | Tiền điều kiện | Các bước thực hiện | Dữ liệu kiểm thử (Test Data) | Kết quả mong đợi | Kết quả thực tế | Trạng thái (Pass/Fail) |
| :---: | :---: | :---: | :--- | :---: | :---: | :---: | :---: | :--- | :--- | :--- | :--- | :---: | :---: |
| **TC_RANK_01** | FR10 | Ranking | Danh sách có điểm | Positive | TB | UI | EP | Có user đã giải bài | Mở trang tìm người dùng | Không có | Mỗi dòng có tên, nhóm, `totalScore`, `totalPuzzles` | — | Chưa chạy |
| **TC_RANK_02** | FR10 | Ranking | Sắp xếp theo điểm giảm dần | Positive | TB | Unit | EP | Có ít nhất 2 user khác điểm | Gọi `GET /users` | Không tham số | User điểm cao đứng trước. **DEF-09**, dự kiến Fail | — | Chưa chạy |
| **TC_RANK_03** | FR10 | Ranking | Tìm theo tên không phân biệt hoa thường | Positive | Thấp | Unit | EP | Có user tên chứa "khoa" | Gọi với `search=KHOA` | `KHOA` | Trả user có tên chứa "khoa" | — | Chưa chạy |
| **TC_RANK_04** | FR10 | Ranking | `limit` vượt biên | Negative | Thấp | Unit | BVA | Không có | Gọi với `limit=0` và `limit=101` | `0`, `101` | Áp dụng 1 và 100 | — | Chưa chạy |
| **TC_RANK_05** | FR10, FR16 | Ranking | Ẩn user bị cấm | Negative | TB | Unit | ST | Có user bị cấm | Tìm theo tên user bị cấm | Tên user bị cấm | Không có trong kết quả | — | Chưa chạy |

## 6. Nhóm thi đấu (FR11)

| Test Case ID | Mã YC | Chức năng | Mô tả ca kiểm thử | Loại test (Pos/Neg) | Ưu tiên | Cấp độ | Kỹ thuật | Tiền điều kiện | Các bước thực hiện | Dữ liệu kiểm thử (Test Data) | Kết quả mong đợi | Kết quả thực tế | Trạng thái (Pass/Fail) |
| :---: | :---: | :---: | :--- | :---: | :---: | :---: | :---: | :--- | :--- | :--- | :--- | :---: | :---: |
| **TC_TEAM_01** | FR11 | Team | Tạo nhóm thành công | Positive | Cao | UI | EP | Đã đăng nhập, chưa có nhóm | 1. Mở trang tạo nhóm<br>2. Nhập tên<br>3. Lưu | Tên mới | Nhóm được tạo, user là chủ nhóm | — | Chưa chạy |
| **TC_TEAM_02** | FR11 | Team | Tên nhóm rỗng | Negative | TB | Unit | BVA | Như TC_TEAM_01 | Tạo với tên rỗng hoặc chỉ khoảng trắng | `""`, `"   "` | 400 `Team name must be 1-100 characters` | — | Chưa chạy |
| **TC_TEAM_03** | FR11 | Team | Tên đúng biên 100 ký tự | Positive | TB | Unit | BVA | Như TC_TEAM_01 | Tạo với tên 100 ký tự | 100 ký tự | Tạo thành công | — | Chưa chạy |
| **TC_TEAM_04** | FR11 | Team | Tên vượt biên 101 ký tự | Negative | TB | Unit | BVA | Như TC_TEAM_01 | Tạo với tên 101 ký tự | 101 ký tự | 400 `Team name must be 1-100 characters` | — | Chưa chạy |
| **TC_TEAM_05** | FR11 | Team | Tên nhóm trùng | Negative | TB | Unit | EP | Đã có nhóm cùng tên | Tạo với tên đã tồn tại | Tên đã có | 409 `A team with this name already exists` | — | Chưa chạy |
| **TC_TEAM_06** | FR11 | Team | Chủ nhóm tạo thêm nhóm | Negative | TB | Unit | DT | User là chủ một nhóm | Tạo nhóm mới | Tên mới | 400 `You already own a team. Disband it first.` | — | Chưa chạy |
| **TC_TEAM_07** | FR11 | Team | Thành viên tạo nhóm trùng tên | Negative | TB | Unit | EG | User là thành viên (không phải chủ) nhóm A | Tạo nhóm với tên đã tồn tại | Tên đã có | 409, user vẫn ở nhóm A. **DEF-10**, dự kiến Fail | — | Chưa chạy |
| **TC_TEAM_08** | FR11 | Team | Tham gia nhóm private | Negative | TB | Unit | EP | Có nhóm private | Gọi `/teams/join` | `teamId` nhóm private | 400 `This team is private. Cannot join.` | — | Chưa chạy |
| **TC_TEAM_09** | FR11 | Team | Tham gia khi đang ở nhóm khác | Negative | TB | Unit | DT | User là thành viên nhóm khác | Tham gia nhóm public | `teamId` hợp lệ | 400 `Already in a team. Leave it first.` | — | Chưa chạy |
| **TC_TEAM_10** | FR11 | Team | Thiếu `teamId` | Negative | Thấp | Unit | EG | Đã đăng nhập | Gọi `/teams/join` không có `teamId` | `{}` | 400 `teamId is required` | — | Chưa chạy |
| **TC_TEAM_11** | FR11 | Team | Nhóm không tồn tại | Negative | Thấp | Unit | EP | Đã đăng nhập, chưa có nhóm | Tham gia `teamId=999999` | `999999` | 404 `Team not found` | — | Chưa chạy |
| **TC_TEAM_12** | FR11 | Team | Chủ nhóm rời nhóm | Negative | TB | Unit | DT | User là chủ nhóm | Gọi `/teams/leave` | Không có | 400 `You are the owner. Transfer ownership or disband the team first.` | — | Chưa chạy |
| **TC_TEAM_13** | FR11 | Team | Thành viên sửa nhóm | Negative | TB | Unit | DT | User là thành viên, không phải chủ | Gọi `PUT /teams/update` | Tên mới | 403 `Only the team owner can edit` | — | Chưa chạy |
| **TC_TEAM_14** | FR11 | Team | Shoutout đúng biên 500 ký tự | Positive | Thấp | Unit | BVA | User là chủ nhóm | Gọi `PUT /teams/shoutout` | 500 ký tự | Cập nhật thành công | — | Chưa chạy |
| **TC_TEAM_15** | FR11 | Team | Shoutout vượt biên 501 ký tự | Negative | Thấp | Unit | BVA | User là chủ nhóm | Gọi `PUT /teams/shoutout` | 501 ký tự | 400 `Shoutout must be at most 500 characters` | — | Chưa chạy |
| **TC_TEAM_16** | FR11 | Team | Chủ nhóm tự kick mình | Negative | Thấp | Unit | EG | User là chủ nhóm | Gọi `/teams/kick` với id của mình | `userId` của mình | 400 `Cannot kick yourself` | — | Chưa chạy |
| **TC_TEAM_17** | FR11 | Team | Chuyển chủ nhóm cho người ngoài | Negative | Thấp | Unit | EP | User là chủ nhóm | Gọi `/teams/transfer` | `newOwnerId` không thuộc nhóm | 400 `New owner must be a team member` | — | Chưa chạy |
| **TC_TEAM_18** | FR11 | Team | Chưa đăng nhập | Negative | TB | Unit | EP | Không có session | Gọi `/teams/create` | Tên hợp lệ | 401 `Not authenticated` | — | Chưa chạy |

## 7. CRUD bài tập và Phân quyền Admin (FR12–FR14, FR16)

| Test Case ID | Mã YC | Chức năng | Mô tả ca kiểm thử | Loại test (Pos/Neg) | Ưu tiên | Cấp độ | Kỹ thuật | Tiền điều kiện | Các bước thực hiện | Dữ liệu kiểm thử (Test Data) | Kết quả mong đợi | Kết quả thực tế | Trạng thái (Pass/Fail) |
| :---: | :---: | :---: | :--- | :---: | :---: | :---: | :---: | :--- | :--- | :--- | :--- | :---: | :---: |
| **TC_CRUD_01** | FR12, UC03 | Create | Thêm bài thành công | Positive | Cao | UI | UC | Đăng nhập Admin | 1. Mở form thêm bài<br>2. Điền thông tin<br>3. Lưu | Tiêu đề, nội dung, `Easy`, điểm 10 | Lưu thành công, hiển thị trên bảng | — | Chưa chạy |
| **TC_CRUD_02** | FR12 | Create | Thiếu tiêu đề | Negative | Cao | Unit | EP | Đăng nhập Admin | Lưu khi để trống tiêu đề | `title=""` | 400 `title is required` | — | Chưa chạy |
| **TC_CRUD_03** | FR12 | Create | Tiêu đề chỉ có khoảng trắng | Negative | TB | Unit | BVA | Đăng nhập Admin | Lưu với tiêu đề khoảng trắng | `title="   "` | 400 `title is required` | — | Chưa chạy |
| **TC_CRUD_04** | FR12 | Create | Độ khó ngoài danh sách | Negative | TB | Unit | EP | Đăng nhập Admin | Gọi API với độ khó lạ | `difficulty=Impossible` | Bị từ chối. **DEF-04**, dự kiến Fail | — | Chưa chạy |
| **TC_CRUD_05** | FR12 | Create | Điểm âm | Negative | TB | Unit | BVA | Đăng nhập Admin | Gọi API với điểm âm | `score=-1` | Bị từ chối. **DEF-05**, dự kiến Fail | — | Chưa chạy |
| **TC_CRUD_06** | FR13, UC03 | Update | Sửa bài thành công | Positive | Cao | UI | UC | Đăng nhập Admin, có bài | 1. Chọn bài<br>2. Sửa, lưu | `id` hợp lệ | Dữ liệu được cập nhật | — | Chưa chạy |
| **TC_CRUD_07** | FR13 | Update | Thiếu `id` | Negative | TB | Unit | EG | Đăng nhập Admin | Gọi `PUT` không có `id` | Không có | 400 `id is required` | — | Chưa chạy |
| **TC_CRUD_08** | FR13 | Update | `id` không tồn tại | Negative | TB | Unit | EP | Đăng nhập Admin | Gọi `PUT` với `id` lạ | `999999` | 404 `Puzzle not found` | — | Chưa chạy |
| **TC_CRUD_09** | FR14, UC03 | Delete | Xóa bài có xác nhận | Positive | Cao | UI | UC | Đăng nhập Admin, có bài | 1. Bấm xóa<br>2. Xác nhận | Bài có sẵn | Bài bị xóa khỏi bảng | — | Chưa chạy |
| **TC_CRUD_10** | FR14 | Delete | Huỷ xóa | Negative | TB | UI | UC | Như TC_CRUD_09 | 1. Bấm xóa<br>2. Huỷ | Bài có sẵn | Bài vẫn còn | — | Chưa chạy |
| **TC_CRUD_11** | FR14 | Delete | `id` không phải số | Negative | Thấp | Unit | EG | Đăng nhập Admin | Gọi `DELETE` với `id=abc` | `abc` | 400 `Invalid id` | — | Chưa chạy |
| **TC_CRUD_12** | FR16, NFR03 | Authz | Chưa đăng nhập gọi API admin | Negative | Cao | Unit | DT | Không có session | Gọi `/admin/puzzles` | Không có | 401 `Not authenticated` | — | Chưa chạy |
| **TC_CRUD_13** | FR16, NFR03 | Authz | User thường gọi API admin | Negative | Cao | Unit | DT | Đăng nhập User | Gọi `/admin/puzzles` | Tài khoản User | 403 `Forbidden. Admin access required.` | — | Chưa chạy |
| **TC_CRUD_14** | FR16 | Authz | User thường mở trang Admin | Negative | Cao | UI | DT | Đăng nhập User | Mở `/frontend/pages/admin/dashboard/index.html` | Tài khoản User | Chuyển về trang chủ user, không hiện nội dung Admin | — | Chưa chạy |

## 8. Quản lý testcase (FR15)

| Test Case ID | Mã YC | Chức năng | Mô tả ca kiểm thử | Loại test (Pos/Neg) | Ưu tiên | Cấp độ | Kỹ thuật | Tiền điều kiện | Các bước thực hiện | Dữ liệu kiểm thử (Test Data) | Kết quả mong đợi | Kết quả thực tế | Trạng thái (Pass/Fail) |
| :---: | :---: | :---: | :--- | :---: | :---: | :---: | :---: | :--- | :--- | :--- | :--- | :---: | :---: |
| **TC_TCM_01** | FR15 | Testcase | Xem testcase của bài | Positive | Cao | UI | EP | Đăng nhập Admin, bài có testcase | Mở danh sách testcase của bài | `puzzle` hợp lệ | Hiển thị input, output từng testcase | — | Chưa chạy |
| **TC_TCM_02** | FR15 | Testcase | Thiếu `puzzle` | Negative | TB | Unit | EG | Đăng nhập Admin | Gọi `GET /admin/testcases` | Không có | 400 `puzzle is required` | — | Chưa chạy |
| **TC_TCM_03** | FR15 | Testcase | `puzzle` không phải số | Negative | Thấp | Unit | EG | Đăng nhập Admin | Gọi với `puzzle=abc` | `abc` | 400 `Invalid puzzle id` | — | Chưa chạy |
| **TC_TCM_04** | FR15 | Testcase | Thêm testcase thành công | Positive | Cao | UI | EP | Đăng nhập Admin, có bài | Thêm testcase có input, output | `puzId` hợp lệ | Trả `Testcase created`, hiển thị trong danh sách | — | Chưa chạy |
| **TC_TCM_05** | FR15 | Testcase | Thêm thiếu `puzId` | Negative | TB | Unit | EG | Đăng nhập Admin | Gọi `POST` không có `puzId` | `{input, output}` | 400 `puzId is required` | — | Chưa chạy |
| **TC_TCM_06** | FR15 | Testcase | Thêm với `puzId` không tồn tại | Negative | TB | Unit | EP | Đăng nhập Admin | Gọi `POST` | `puzId=999999` | 404. **DEF-12**, dự kiến Fail | — | Chưa chạy |
| **TC_TCM_07** | FR15 | Testcase | Thêm thiếu input và output | Negative | TB | Unit | EG | Đăng nhập Admin | Gọi `POST` chỉ có `puzId` | `{puzId}` | 400. **DEF-13**, dự kiến Fail | — | Chưa chạy |
| **TC_TCM_08** | FR15 | Testcase | Sửa testcase không tồn tại | Negative | Thấp | Unit | EP | Đăng nhập Admin | Gọi `PUT` với `id=999999` | `999999` | 404 `Testcase not found` | — | Chưa chạy |
| **TC_TCM_09** | FR15 | Testcase | Xóa thiếu `id` | Negative | Thấp | Unit | EG | Đăng nhập Admin | Gọi `DELETE` không có `id` | Không có | 400 `id is required` | — | Chưa chạy |
| **TC_TCM_10** | FR15 | Testcase | Xem với `id` không phải số | Negative | Thấp | Unit | EG | Đăng nhập Admin | Gọi `GET /admin/testcase?id=abc` | `abc` | 400 `Invalid id` | — | Chưa chạy |

## 9. Quản lý người dùng (FR16)

| Test Case ID | Mã YC | Chức năng | Mô tả ca kiểm thử | Loại test (Pos/Neg) | Ưu tiên | Cấp độ | Kỹ thuật | Tiền điều kiện | Các bước thực hiện | Dữ liệu kiểm thử (Test Data) | Kết quả mong đợi | Kết quả thực tế | Trạng thái (Pass/Fail) |
| :---: | :---: | :---: | :--- | :---: | :---: | :---: | :---: | :--- | :--- | :--- | :--- | :---: | :---: |
| **TC_USR_01** | FR16 | User mgmt | Xem danh sách người dùng | Positive | Cao | UI | EP | Đăng nhập Admin | Mở tab người dùng trên dashboard | Không có | Hiển thị danh sách người dùng | — | Chưa chạy |
| **TC_USR_02** | FR16 | User mgmt | Xem chi tiết thiếu tham số | Negative | Thấp | Unit | EG | Đăng nhập Admin | Gọi `GET /admin/user` không tham số | Không có | 400 `Provide id, username, or email` | — | Chưa chạy |
| **TC_USR_03** | FR16 | User mgmt | Xem chi tiết user không tồn tại | Negative | Thấp | Unit | EP | Đăng nhập Admin | Gọi với `id=999999` | `999999` | 404 `User not found` | — | Chưa chạy |
| **TC_USR_04** | FR16 | User mgmt | Cấm tài khoản | Positive | Cao | UI | ST | Đăng nhập Admin; có User active | 1. Cấm User<br>2. User đó đăng nhập | `id` hợp lệ | Trả `User banned`; lần đăng nhập sau bị 403 `Account is banned` | — | Chưa chạy |
| **TC_USR_05** | FR16 | User mgmt | Mở lại tài khoản | Positive | Cao | UI | ST | Có User bị cấm | 1. Mở lại<br>2. User đó đăng nhập | `id` của User bị cấm | Trả `User reactivated`; đăng nhập thành công | — | Chưa chạy |
| **TC_USR_06** | FR16 | User mgmt | Cấm thiếu `id` | Negative | TB | Unit | EG | Đăng nhập Admin | Gọi `PUT /admin/user/ban` không có `id` | Không có | 400 `id is required` | — | Chưa chạy |
| **TC_USR_07** | FR16 | User mgmt | Cấm với `id` không phải số | Negative | Thấp | Unit | EG | Đăng nhập Admin | Gọi với `id=abc` | `abc` | 400 `Invalid id` | — | Chưa chạy |
| **TC_USR_08** | FR16 | User mgmt | Cấm user không tồn tại | Negative | TB | Unit | EP | Đăng nhập Admin | Gọi với `id=999999` | `999999` | 404. **DEF-14**, dự kiến Fail | — | Chưa chạy |
| **TC_USR_09** | FR16 | User mgmt | Vô hiệu hoá là xóa mềm | Positive | TB | Unit | ST | Đăng nhập Admin; có User | 1. Gọi `DELETE /admin/user`<br>2. Xem chi tiết user đó | `id` hợp lệ | Trả `User deactivated`; bản ghi vẫn còn, `userIsactive=false` | — | Chưa chạy |

## 10. Phi chức năng (NFR01–NFR05)

| Test Case ID | Mã YC | Chức năng | Mô tả ca kiểm thử | Loại test (Pos/Neg) | Ưu tiên | Cấp độ | Kỹ thuật | Tiền điều kiện | Các bước thực hiện | Dữ liệu kiểm thử (Test Data) | Kết quả mong đợi | Kết quả thực tế | Trạng thái (Pass/Fail) |
| :---: | :---: | :---: | :--- | :---: | :---: | :---: | :---: | :--- | :--- | :--- | :--- | :---: | :---: |
| **TC_NFR_01** | NFR01 | Performance | Thời gian phản hồi | Positive | TB | Manual | BVA | Hệ thống chạy local, DB đã bật | Đo `GET /puzzles` và tải trang bài tập 10 lần | Không có | Mỗi lần dưới 3 giây | — | Chưa chạy |
| **TC_NFR_02** | NFR02 | Responsive | Hiển thị trên mobile và desktop | Positive | TB | Manual | EP | Hệ thống chạy local | Mở đăng nhập, bài tập, giải bài ở 375px và 1366px | 2 độ rộng | Không tràn ngang, nút bấm được, chữ đọc được | — | Chưa chạy |
| **TC_NFR_03** | NFR03 | Security | Không lộ thông tin nhạy cảm | Positive | Cao | Unit | EG | Đã đăng nhập | Gọi `/auth/me` và `/admin/user` | Không có | Phản hồi không có mật khẩu hay token; mật khẩu chỉ gửi tới Firebase | — | Chưa chạy |
| **TC_NFR_04** | NFR04 | Stability | Nhiều request đồng thời (kiểm tra khói) | Positive | Thấp | Manual | EP | Hệ thống chạy local | Gửi 20 request `GET /puzzles` cùng lúc | 20 request | Tất cả trả 200, không lỗi 500 | — | Chưa chạy |
| **TC_NFR_05** | NFR05 | Usability | Dễ sử dụng trên luồng chính | Positive | Thấp | Manual | UC | Có tài khoản mới | Đăng ký → tìm bài → giải bài → xem kết quả, không xem hướng dẫn | Không có | Hoàn thành luồng, mọi lỗi đều có thông báo rõ ràng | — | Chưa chạy |

## TỔNG HỢP
| Nhóm | Số ca |
| :--- | :---: |
| Đăng ký | 9 |
| Đăng nhập, đăng xuất, quên mật khẩu | 16 |
| Bài tập | 12 |
| Viết code, nộp bài, chạy thử | 17 |
| Bảng xếp hạng | 5 |
| Nhóm | 18 |
| CRUD bài tập, phân quyền | 14 |
| Testcase | 10 |
| Người dùng | 9 |
| Phi chức năng | 5 |
| **Tổng** | **115** |
