# Nhật ký AI: AI review và đề xuất tối ưu mã nguồn test

**Công cụ:** Claude (Claude Code).
**Tình trạng:** Mới review 3 file test mẫu của template, vì chưa có test thật của DevClimb. Cần review lại ở Tuần 6 khi đã có code test.

## 1. Ngữ cảnh & Mục tiêu
- Review `SampleControllerTest.java`, `LoginPage.java`, `LoginTest.java` để xác định cần thay đổi gì khi áp dụng cho DevClimb.

## 2. Prompt hoàn chỉnh (soạn lại từ yêu cầu đã gửi)
> Bạn là một Senior QA Automation Engineer. Hãy review ba file kiểm thử mẫu của template SOF309: `SampleControllerTest.java`, `LoginPage.java` và `LoginTest.java`, đối chiếu với dự án DevClimb (Servlet backend, frontend HTML/JS thuần, đăng nhập bằng email qua Firebase, trang đăng nhập ở `/frontend/pages/guest/auth/login.html`). Với mỗi file, nêu: điểm chưa phù hợp với DevClimb, vấn đề về selector, cơ chế chờ (`WebDriverWait` so với `implicitlyWait`), khả năng chạy headless trên CI, dữ liệu viết cứng, và khả năng kiểm thử thật của controller (phụ thuộc khởi tạo DAO trực tiếp). Đề xuất cách sửa cụ thể, chưa cần viết lại mã.

## 3. Kết quả do AI sinh ra (Raw Output)
**`SampleControllerTest.java`**
1. Hàm `authenticate()` là hàm giả nằm ngay trong class test, nên test không kiểm tra mã nguồn thật nào.
2. `pom.xml` gốc chưa có các class của `backend/` trên classpath test, cũng chưa có Mockito và Servlet API, nên chưa thể gọi `AuthController`.
3. Controller khởi tạo DAO trực tiếp (`new UserDao()`) và `init()` gọi `FirebaseService`, nên khó giả lập. Cần cách tiêm phụ thuộc hoặc tách logic kiểm tra ra hàm riêng.

**`LoginPage.java`**
1. Locator không khớp DevClimb: `username` và `error-message` phải đổi thành `email` và `loginError`.
2. Không dùng `WebDriverWait` như yêu cầu Tuần 3.
3. `getErrorMessage()` đọc ngay sau khi bấm, trong khi Firebase xử lý bất đồng bộ, nên có thể đọc phải chuỗi rỗng. Cần chờ đến khi `#loginError` có nội dung.

**`LoginTest.java`**
1. `BASE_URL` sai với DevClimb: trang đăng nhập là `/frontend/pages/guest/auth/login.html`, phục vụ ở cổng 5500, không phải `localhost:8080/login`.
2. Dữ liệu `admin` / `123` không hợp lệ vì DevClimb đăng nhập bằng email.
3. Dùng `implicitlyWait`; khi thêm `WebDriverWait` không nên trộn hai loại chờ vì thời gian chờ có thể khó đoán.
4. `window().maximize()` không đáng tin ở chế độ headless; nên dùng `--window-size`.
5. URL và tài khoản đang viết cứng; nên đưa ra cấu hình và không commit mật khẩu thật.

## 4. Chỉnh sửa của Sinh viên & Lý do sửa
**Sinh viên tự điền khi làm Tuần 6:**
- Những điểm AI làm sai hoặc thiếu: ...
- Code / Tài liệu hoàn thiện sau khi bạn sửa: ...
