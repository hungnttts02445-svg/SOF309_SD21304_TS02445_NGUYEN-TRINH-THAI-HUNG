# Nhật ký AI: Dùng AI viết workflow CI/CD GitHub Actions

**Công cụ:** Claude (Claude Code).
**Tình trạng:** Mới có phần AI phân tích workflow hiện tại. Workflow Tuần 5 chưa được viết và chưa chạy trên GitHub; bổ sung tiếp khi làm Tuần 5.

## 1. Ngữ cảnh & Mục tiêu
- Phân tích `.github/workflows/test.yml` của template so với yêu cầu Tuần 5 và cấu trúc dự án DevClimb.
- Thay đổi đã thực hiện: đổi JDK từ 17 sang 21 để khớp `pom.xml` gốc.

## 2. Prompt hoàn chỉnh (soạn lại từ yêu cầu đã gửi)
> Bạn là một kỹ sư DevOps có kinh nghiệm với GitHub Actions và Maven. Hãy phân tích file `.github/workflows/test.yml` của repo SOF309 so với yêu cầu Tuần 5: chạy test ở chế độ Chrome headless trên Ubuntu, chạy riêng nhóm `smoke`, upload báo cáo kiểm thử và thư mục `screenshots/` lên tab Artifacts. Dự án gồm `pom.xml` gốc (JUnit 5, Selenium, Java 21) cho phần test và `backend/pom.xml` riêng cho ứng dụng DevClimb (WAR, Tomcat 11), cần database và các file cấu hình bí mật bị gitignore. Hãy chỉ ra những điểm workflow hiện tại chưa đáp ứng, rủi ro khiến pipeline thất bại hoặc xanh giả, và đề xuất cách sửa. Chưa viết workflow hoàn chỉnh vì Tuần 5 chưa bắt đầu.

## 3. Kết quả do AI sinh ra (Raw Output)
Nhận xét của AI về workflow hiện tại:
1. `mvn clean test` chạy ở thư mục gốc, chỉ biên dịch và chạy `src/test`. `backend/` có `pom.xml` riêng và không thuộc build này, nên CI hiện không cần DB.
2. Workflow chỉ upload `target/surefire-reports/`. Yêu cầu Tuần 5 cần thêm `screenshots/` và artifact tên `test-execution-results`.
3. Chưa có bước chạy theo nhóm `smoke`.
4. Các file cấu hình (`db.properties`, `firebase-service-account.json`, `r2.properties`, `ca-truststore.jks`, `constants.js`) bị gitignore nên CI không có. Ứng dụng không khởi động được trên CI nếu không cấu hình GitHub Secrets hoặc giả lập.
5. Vì vậy test Selenium trỏ tới `localhost` sẽ thất bại trên CI khi không có ứng dụng chạy. Cần tách nhóm test không phụ thuộc hạ tầng để pipeline xanh một cách thực chất.
6. GitHub Actions đặt `CI=true`, nên điều kiện bật headless trong `LoginTest` hoạt động đúng.
7. Khi thêm TestNG cùng JUnit 5, cần cấu hình Surefire để cả hai loại test đều thực sự được chạy, và kiểm tra số test trong báo cáo.

## 4. Chỉnh sửa của Sinh viên & Lý do sửa
**Sinh viên tự điền khi làm Tuần 5:**
- Những điểm AI làm sai hoặc thiếu: ...
- Code / Tài liệu hoàn thiện sau khi bạn sửa: ...
