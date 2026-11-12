# Nhật ký AI: Dùng AI + RAG sinh Test Plan & Test Case

**Công cụ:** Claude (Claude Code), làm việc trực tiếp trên mã nguồn trong repo.

## 1. Ngữ cảnh & Mục tiêu
- Tích hợp dự án DevClimb vào repo SOF309, sau đó hoàn thành Y2: `docs/SPECIFICATIONS.md`, `docs/TEST_PLAN.md`, `docs/TEST_CASES.md`.
- Ngữ cảnh cung cấp cho AI (RAG): `docs/SRS.md`, mã nguồn `backend/` và `frontend/`, báo cáo đồ thị mã nguồn của graphify.
- Chức năng áp dụng: Đăng ký, Đăng nhập, Bài tập, Nộp bài, CRUD bài tập của Admin.

## 2. Prompt hoàn chỉnh (soạn lại từ các yêu cầu đã gửi)

**Prompt 1: Đặc tả nghiệp vụ**
> Bạn là một QA Lead có kinh nghiệm kiểm thử ứng dụng web Java. Tôi đang thực hiện Assignment môn SOF309 cho dự án DevClimb, một nền tảng luyện giải bài lập trình, chấm bài tự động bằng Judge0. Tài liệu đầu vào gồm: `docs/SRS.md`, mã nguồn `backend/` (Jakarta Servlet, Hibernate/JPA) và `frontend/` (HTML/CSS/JS thuần). Hãy viết `docs/SPECIFICATIONS.md` bằng tiếng Việt gồm: thông tin dự án, danh sách màn hình và API, quy tắc nghiệp vụ theo từng chức năng (đăng ký, đăng nhập, bài tập, nộp bài, CRUD Admin), mã lỗi và thông báo chính xác lấy từ mã nguồn. Khi mã nguồn khác SRS, hãy mô tả theo mã nguồn và liệt kê riêng điểm lệch. Không suy đoán những gì mã nguồn không thể hiện.

**Prompt 2: Test Plan và Test Case**
> Dựa trên `docs/SPECIFICATIONS.md`, hãy áp dụng kỹ thuật Phân vùng tương đương và Phân tích giá trị biên để thiết kế Test Plan và bộ Test Case chi tiết cho cả Controller (Unit) và giao diện (UI). Mỗi ca cần có: mã ca, mã yêu cầu, mô tả, loại (Positive/Negative), ưu tiên, cấp độ, kỹ thuật, tiền điều kiện, các bước, dữ liệu kiểm thử và kết quả mong đợi. Với mỗi giá trị biên, nêu cả hai phía của biên (ví dụ `limit` bằng 0, 1, 100 và 101). Đánh dấu các ca dự kiến Fail vì mã nguồn lệch đặc tả.

**Prompt 3: Rà soát và viết lại Test Plan (lần 2)**
> Hãy rà soát lại toàn bộ các file trong `docs/`. Test Plan hiện còn thiếu nhiều mục của một kế hoạch kiểm thử chuẩn. Hãy viết lại theo IEEE 829 / ISO/IEC/IEEE 29119-3, gồm: định danh và lịch sử phiên bản, tài liệu tham chiếu, đối tượng kiểm thử, chức năng được và không được kiểm thử kèm lý do, cách tiếp cận, tiêu chí đạt/không đạt, entry/exit, điều kiện tạm dừng và tiếp tục, sản phẩm bàn giao, môi trường, dữ liệu kiểm thử, quản lý lỗi, rủi ro, lịch, vai trò, chỉ số đo, ma trận truy vết và phê duyệt. Mở rộng Test Case để phủ đủ FR01–FR16 và NFR01–NFR05.

**Prompt 4: Kiểm tra độ phủ bằng đồ thị mã nguồn**
> Hãy sử dụng đồ thị graphify của dự án DevClimb (`graphify-out`) để liệt kê đầy đủ các controller, filter và quan hệ DAO/entity. Đối chiếu với Test Plan để chắc chắn không bỏ sót chức năng nào, và ghi lý do cho các thành phần được loại khỏi phạm vi kiểm thử.

## 3. Kết quả do AI sinh ra (Raw Output)
Kết quả đầy đủ nằm trong 3 file:
- `docs/SPECIFICATIONS.md`: vai trò, FR01–FR16, UC01–UC03, màn hình, API, quy tắc nghiệp vụ, dữ liệu chính, NFR, 14 điểm lệch DEF-01 đến DEF-14.
- `docs/TEST_PLAN.md`: viết lại theo IEEE 829 / ISO/IEC/IEEE 29119-3 (19 mục, gồm tiêu chí entry/exit, tạm dừng, quản lý lỗi, rủi ro, ma trận truy vết).
- `docs/TEST_CASES.md`: 115 ca kiểm thử phủ FR01–FR16 và NFR01–NFR05, có cột Mã YC, Ưu tiên, Cấp độ, Kỹ thuật, Tiền điều kiện, Kết quả thực tế; trạng thái ban đầu là "Chưa chạy".

Lần 1 chỉ có 59 ca và Test Plan 7 mục, phủ 5 nhóm chức năng. Lần 2 dùng skill `ecc:test-collaboration` (truy vết yêu cầu tới Test ID) và đồ thị graphify để liệt kê đủ 30 controller và filter, xác định phạm vi và các chức năng còn thiếu.

Các giá trị biên AI rút ra từ mã nguồn:
- `limit`: 0, 1, 100, 101 (`PuzzleController`).
- `page`: 0, 1 (`PuzzleController`).
- `user_code`: 100.000 và 100.001 ký tự (`SubmissionController`).
- Khoảng cách giữa hai lần nộp: dưới và từ 2000 ms (`RATE_LIMIT_MS`).
- Mật khẩu Firebase: 5 và 6 ký tự.

Các điểm mã nguồn lệch SRS do AI phát hiện: xem DEF-01 đến DEF-14 trong `docs/SPECIFICATIONS.md` mục 7. Lần 2 phát hiện thêm:
- Không có chức năng chạy thử code (FR08).
- `/users` sắp theo tên, không xếp hạng theo điểm (FR10). Phát hiện nhờ đồ thị: `UserSearchController` chỉ gọi `UserDao.search`, có `ORDER BY u.user_name ASC`.
- Tạo nhóm: thành viên bị rời nhóm cũ trước khi kiểm tra trùng tên.
- Quên mật khẩu: bỏ trống email làm nút gửi bị khoá luôn.
- Tạo testcase không kiểm tra `puzId` tồn tại, không bắt buộc input/output.
- Cấm người dùng không tồn tại vẫn trả thành công.

## 4. Chỉnh sửa của Sinh viên & Lý do sửa
**AI tự phát hiện và sửa trong quá trình làm (không phải sinh viên sửa):**
- Bản nháp đặc tả ghi `lang_id` không hỗ trợ sẽ trả 400 `Language not supported`. Sau khi đọc `SubmissionController`, sửa lại: `lang_id` không có trong bảng ánh xạ sẽ mặc định dùng Python.
- Bản nháp chỉ ghi chung User thường bị `AuthFilter` từ chối. Sau khi đọc `AuthFilter`, tách thành 401 và 403 với thông báo chính xác.
- Bản nháp chưa có luồng thay thế UC02 (Runtime Error, Time Limit Exceeded, lỗi kết nối Judge0). Sau khi đọc phần Use Case của SRS, bổ sung TC_SUB_12 đến TC_SUB_15.
- Test Plan lần 1 thiếu nhiều mục bắt buộc (sinh viên chỉ ra). Lần 2 viết lại theo IEEE 829.
- Lần 1 AI tự grep từng controller thay vì dùng đồ thị graphify đã có (sinh viên chỉ ra). Lần 2 dùng đồ thị để kiểm tra độ phủ.

**Sinh viên tự điền:**
- Những điểm AI làm sai hoặc thiếu: ...
- Code / Tài liệu hoàn thiện sau khi bạn sửa: ...
