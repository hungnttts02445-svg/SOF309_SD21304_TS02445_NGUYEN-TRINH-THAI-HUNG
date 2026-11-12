# KẾ HOẠCH KIỂM THỬ (TEST PLAN)

> Theo cấu trúc IEEE 829 / ISO/IEC/IEEE 29119-3.

## 1. ĐỊNH DANH TÀI LIỆU
| Mục | Nội dung |
| :--- | :--- |
| Mã tài liệu | TP-DEVCLIMB-001 |
| Dự án | DevClimb - Hệ thống viết code và chấm code trực tuyến bằng Judge0 |
| Môn học | SOF309 - Kiểm thử nâng cao cùng AI |
| Sinh viên | Trần Anh Khoa - TS02448 - Lớp SD21304 |
| Giảng viên | ThS. Trần Quang Bình |

### Lịch sử phiên bản
| Phiên bản | Ngày | Người thực hiện | Thay đổi |
| :---: | :---: | :--- | :--- |
| 1.0 | 11/11/2026 | Trần Anh Khoa (hỗ trợ bởi AI) | Bản đầu tiên, phủ FR01–FR16 và NFR01–NFR05 |

## 2. GIỚI THIỆU
Tài liệu mô tả phạm vi, cách tiếp cận, nguồn lực và lịch kiểm thử cho DevClimb, một nền tảng luyện giải bài lập trình và thi đấu nhóm nhỏ. Kiểm thử gồm Unit Test cho Controller (JUnit 5), UI Test tự động (Selenium WebDriver, Page Object Model, TestNG) và chạy tự động trên GitHub Actions, theo lộ trình 6 tuần của môn học.

## 3. TÀI LIỆU THAM CHIẾU
| Tài liệu | Vai trò |
| :--- | :--- |
| `docs/SRS.md` | Yêu cầu gốc FR01–FR16, NFR01–NFR05, UC01–UC03 |
| `docs/SPECIFICATIONS.md` | Quy tắc nghiệp vụ chi tiết theo mã nguồn, danh sách điểm lệch DEF-01 đến DEF-14 |
| `docs/TEST_CASES.md` | Bộ ca kiểm thử chi tiết |
| `README.md`, `docs/HUONG_DAN_CHI_TIET_6_TUAN.md` | Yêu cầu và lịch của môn học |
| IEEE 829 / ISO/IEC/IEEE 29119-3 | Cấu trúc tài liệu kiểm thử |

## 4. ĐỐI TƯỢNG KIỂM THỬ (TEST ITEMS)
| Thành phần | Phiên bản / Vị trí |
| :--- | :--- |
| Backend DevClimb | `backend/` (`devclimb-backend` 1.0.0, WAR) |
| Frontend DevClimb | `frontend/` |
| Controller và filter trong phạm vi | `AuthController`, `PuzzleController`, `SubmissionController`, `UserSearchController`, `TeamBrowseController`, `TeamManageController`, `AdminPuzzleController`, `AdminTestcaseController`, `AdminUserController`, `AuthFilter` |

## 5. CHỨC NĂNG ĐƯỢC KIỂM THỬ
| Yêu cầu | Chức năng | Mức rủi ro |
| :--- | :--- | :---: |
| FR01 | Đăng ký | Cao |
| FR02 | Đăng nhập, đăng xuất, quên mật khẩu, phiên làm việc | Cao |
| FR03–FR06 | Danh sách, tìm kiếm, lọc, chi tiết bài tập | Trung bình |
| FR07, FR09 | Viết code, nộp bài, nhận kết quả | Cao |
| FR08 | Chạy thử code | Trung bình |
| FR10 | Bảng xếp hạng, tìm người dùng | Thấp |
| FR11 | Nhóm thi đấu | Trung bình |
| FR12–FR14 | CRUD bài tập (Admin) | Cao |
| FR15 | Quản lý testcase (Admin) | Cao |
| FR16 | Quản lý người dùng, cấm và mở lại tài khoản (Admin) | Cao |
| NFR01–NFR05 | Hiệu năng cơ bản, responsive, bảo mật, ổn định, dễ dùng | Trung bình |

## 6. CHỨC NĂNG KHÔNG KIỂM THỬ
| Chức năng | Lý do |
| :--- | :--- |
| Cuộc thi, trophy, Hall of Fame (`AdminContestController`, `AdminTrophyController`, `PublicContestController`) | Không thuộc FR01–FR16 của SRS |
| Thông báo, bảo trì, cảnh cáo, kháng nghị (`AdminAnnouncementController`, `PublicAnnouncementController`, `AdminMaintenanceController`, `PublicMaintenanceController`, `AdminWarningController`, `UserWarningController`, `AppealController`, `AdminAppealController`) | Không thuộc FR01–FR16 |
| Upload ảnh (`UploadController`, `TeamUploadController`, `ContestAvatarUploadController`) | Phụ thuộc Cloudflare R2, không thuộc yêu cầu |
| Thống kê Admin (`AdminController`, `AdminProgressController`, `AdminTeamController`) | Ngoài FR15–FR16 |
| Load test, stress test, kiểm thử bảo mật chuyên sâu | Ngoài phạm vi môn học; NFR04 chỉ kiểm tra khói |
| Nội bộ Firebase, Judge0, Cloudflare R2 | Dịch vụ bên thứ ba; chỉ kiểm tra phần tích hợp |

## 7. CÁCH TIẾP CẬN (TEST APPROACH)

### 7.1 Cấp độ kiểm thử
| Cấp độ | Công cụ | Đối tượng | Tuần |
| :--- | :--- | :--- | :---: |
| Unit | JUnit 5 (`@ParameterizedTest`, `assertThrows`, `@Timeout`) | Các nhánh kiểm tra dữ liệu và phân quyền của controller | 2 |
| UI | Selenium WebDriver, Page Object Model, `WebDriverWait` | Đăng nhập, đăng ký, bài tập, giải bài, CRUD Admin | 3 |
| UI nâng cao | TestNG (`groups`, `dependsOnMethods`, `@DataProvider`, `ITestListener`) | Nhóm smoke và regression, chụp ảnh khi lỗi | 4 |
| CI | GitHub Actions, Chrome headless | Chạy nhóm test không cần hạ tầng ngoài | 5 |
| Thủ công | Trình duyệt, DevTools | NFR02, NFR05 và các ca khó tự động | 1–6 |

### 7.2 Kỹ thuật thiết kế
| Kỹ thuật | Áp dụng |
| :--- | :--- |
| Phân vùng tương đương (EP) | Vai trò Guest/User/Admin; email đúng/sai; `id` hợp lệ, không phải số, không tồn tại; độ khó; nhóm public/private |
| Phân tích giá trị biên (BVA) | `limit` 0/1/100/101; `page` 0/1; `user_code` 100.000/100.001 ký tự; khoảng cách nộp bài dưới và từ 2000 ms; mật khẩu 5/6 ký tự; tên nhóm 100/101 ký tự; shoutout 500/501 ký tự |
| Bảng quyết định | Phân quyền: chưa đăng nhập, User, chủ nhóm, Admin |
| Chuyển trạng thái | Tài khoản active → banned → reactivated; thành viên nhóm → chủ nhóm |
| Đoán lỗi | Body JSON sai, thiếu trường, sai kiểu dữ liệu |

### 7.3 Nhóm thực thi
* **smoke:** ca ưu tiên Cao trên luồng chính (đăng nhập, xem bài tập, nộp bài đúng, thêm bài tập).
* **regression:** toàn bộ ca còn lại.

### 7.4 Giả lập
Unit Test giả lập Firebase, Judge0 và DAO; UI Test chạy với dịch vụ thật trên môi trường local.

## 8. TIÊU CHÍ ĐẠT / KHÔNG ĐẠT
* Một ca **Pass** khi kết quả thực tế khớp hoàn toàn kết quả mong đợi (mã HTTP, thông báo, trạng thái dữ liệu, trang chuyển đến).
* Một ca **Fail** khi có bất kỳ sai khác nào; ghi lỗi theo mục 13.
* Một ca **Blocked** khi không chạy được do môi trường hoặc lỗi chặn khác.
* Các ca gắn DEF-xx dự kiến Fail cho đến khi mã nguồn được sửa.

## 9. TIÊU CHÍ BẮT ĐẦU VÀ KẾT THÚC
**Bắt đầu (Entry):**
* Ứng dụng chạy được trên máy local (backend 8080, frontend 5500).
* Database, Firebase, Judge0 truy cập được.
* Dữ liệu kiểm thử ở mục 12 đã sẵn sàng.
* `SPECIFICATIONS.md` và `TEST_CASES.md` đã được rà soát.

**Kết thúc (Exit):**
* 100% ca ưu tiên Cao đã thực thi; tối thiểu 95% toàn bộ ca đã thực thi.
* Không còn lỗi mức Critical đang mở.
* Mọi ca Fail đều có lỗi được ghi nhận hoặc thuộc danh sách DEF-xx.
* Pipeline GitHub Actions báo xanh với nhóm test được chọn.

## 10. ĐIỀU KIỆN TẠM DỪNG VÀ TIẾP TỤC
| Tạm dừng khi | Tiếp tục khi |
| :--- | :--- |
| Database, Firebase hoặc Judge0 không truy cập được | Dịch vụ hoạt động lại; chạy lại ca đang dở |
| Lỗi chặn ở đăng nhập khiến các ca User/Admin không chạy được | Lỗi được sửa và TC_AUTH_01 Pass |
| Hơn 30% ca trong một lần chạy bị Blocked | Đã xử lý nguyên nhân môi trường |

## 11. SẢN PHẨM BÀN GIAO
| Sản phẩm | Vị trí | Tuần |
| :--- | :--- | :---: |
| Đặc tả, Test Plan, Test Case | `docs/` | 1 |
| Unit Test | `src/test/java/com/poly/sof309/controllers/` | 2 |
| Page Object và kịch bản UI | `src/test/java/com/poly/sof309/ui/` | 3–4 |
| Cấu hình TestNG | `testng.xml` | 4 |
| Ảnh chụp lỗi | `screenshots/` | 4–5 |
| Workflow CI, báo cáo kiểm thử | `.github/workflows/test.yml`, Artifacts | 5 |
| Minh chứng AI | `docs/ai-logs/` | 1–6 |
| Báo cáo tổng kết, slide | Nộp Google Classroom | 6 |

## 12. MÔI TRƯỜNG VÀ DỮ LIỆU KIỂM THỬ

### 12.1 Môi trường
| Thành phần | Cấu hình |
| :--- | :--- |
| Hệ điều hành | Windows 11; Ubuntu (GitHub Actions) |
| Java | JDK 25 (biên dịch mục tiêu Java 21) |
| Build | Maven 3.9 |
| Máy chủ | Apache Tomcat 11, context `devclimb-backend`, cổng 8080 |
| Frontend | Máy chủ tĩnh cổng 5500, mở bằng `localhost` |
| Database | MySQL 8 (Aiven) |
| Trình duyệt | Google Chrome; Chrome headless trên CI |

### 12.2 Dữ liệu kiểm thử
| Dữ liệu | Mục đích |
| :--- | :--- |
| 1 tài khoản User thường, 1 tài khoản User thứ hai | Luồng User, nhóm, kick, chuyển chủ |
| 1 tài khoản Admin | CRUD bài tập, testcase, người dùng |
| 1 tài khoản bị cấm | TC_AUTH_07, TC_USR_04 |
| 1 bài tập có test case, 1 bài tập không có test case | Nộp bài |
| 1 nhóm public, 1 nhóm private | Tham gia nhóm |
| Chuỗi 100/101 ký tự, 500/501 ký tự, code 100.000/100.001 ký tự | Giá trị biên, sinh bằng script |

Mật khẩu thật của tài khoản kiểm thử không ghi vào tài liệu hay mã nguồn được commit.

## 13. QUẢN LÝ LỖI
| Mức nghiêm trọng | Định nghĩa |
| :--- | :--- |
| Critical | Hệ thống sập, mất dữ liệu, lộ thông tin bảo mật |
| Major | Chức năng chính sai, không có cách làm khác |
| Minor | Sai lệch nhỏ, có cách làm khác |
| Trivial | Lỗi hiển thị, chính tả |

* **Độ ưu tiên sửa:** P1 (sửa ngay), P2 (sửa trong vòng lặp hiện tại), P3 (sửa khi có thời gian).
* **Vòng đời:** New → Open → Fixed → Retest → Closed; nếu kiểm tra lại vẫn lỗi → Reopened.
* **Mẫu báo lỗi:** Mã lỗi, Test Case liên quan, môi trường, các bước tái hiện, kết quả mong đợi, kết quả thực tế, mức nghiêm trọng, ảnh chụp hoặc log.
* **Lỗi đã biết trước khi thực thi:** DEF-01 đến DEF-14 trong `SPECIFICATIONS.md` mục 7.

## 14. LỊCH VÀ CÔNG VIỆC
| Tuần | Thời gian (theo README) | Công việc |
| :---: | :--- | :--- |
| 1 | 02/11 – 08/11 | Tích hợp web, đặc tả, Test Plan, Test Case |
| 2 | 09/11 – 15/11 | Unit Test controller |
| 3 | 16/11 – 22/11 | Selenium Page Object Model |
| 4 | 23/11 – 29/11 | TestNG, Data-Driven, chụp ảnh lỗi |
| 5 | 30/11 – 06/12 | GitHub Actions, debug cùng AI |
| 6 | 07/12 – 09/12 | AI code review, hoàn thiện hồ sơ, slide |
| 7 | 11/12, 14/12, 16/12 | Bảo vệ |

## 15. VAI TRÒ VÀ TRÁCH NHIỆM
| Vai trò | Người | Trách nhiệm |
| :--- | :--- | :--- |
| Tester, Developer | Trần Anh Khoa | Thiết kế, thực thi, ghi lỗi, viết mã kiểm thử |
| Người duyệt | ThS. Trần Quang Bình | Duyệt kế hoạch và kết quả, chấm điểm |
| Trợ lý AI | Claude | Hỗ trợ thiết kế và review; mọi kết quả do sinh viên kiểm chứng |

## 16. RỦI RO VÀ PHƯƠNG ÁN DỰ PHÒNG
| Rủi ro | Khả năng | Ảnh hưởng | Phương án |
| :--- | :---: | :---: | :--- |
| Database Aiven tự tắt khi không dùng | Cao | Cao | Bật lại trên Aiven trước mỗi lần chạy; Unit Test không phụ thuộc DB |
| Firebase, Judge0 chậm hoặc lỗi | Trung bình | Cao | Giả lập ở Unit Test; UI Test có `WebDriverWait` và chạy lại |
| Controller khởi tạo DAO trực tiếp, khó giả lập | Cao | Trung bình | Tách logic kiểm tra hoặc thêm điểm tiêm phụ thuộc |
| CI không có DB và file bí mật | Cao | Trung bình | Chỉ chạy nhóm không cần hạ tầng ngoài trên CI |
| Dữ liệu dùng chung với hệ thống thật | Trung bình | Cao | Dùng tài khoản và dữ liệu riêng cho kiểm thử, dọn dẹp sau khi chạy |
| Mã nguồn lệch SRS | Đã xảy ra | Trung bình | Ghi DEF-xx, giữ ca kiểm thử theo yêu cầu |

## 17. CHỈ SỐ ĐO
* Tỷ lệ thực thi = số ca đã chạy / tổng số ca.
* Tỷ lệ Pass = số ca Pass / số ca đã chạy.
* Độ phủ yêu cầu = số FR/NFR có ít nhất một ca Pass / 21.
* Số lỗi theo mức nghiêm trọng và trạng thái.

## 18. MA TRẬN TRUY VẾT
| Yêu cầu | Test Case |
| :--- | :--- |
| FR01 | TC_REG_01 – TC_REG_09 |
| FR02 | TC_AUTH_01 – TC_AUTH_12, TC_FPW_01 – TC_FPW_04 |
| FR03 | TC_PUZ_01, TC_PUZ_04 – TC_PUZ_09 |
| FR04 | TC_PUZ_03 |
| FR05 | TC_PUZ_02 |
| FR06 | TC_PUZ_10 – TC_PUZ_12 |
| FR07 | TC_SUB_01, TC_SUB_06, TC_SUB_07, TC_SUB_12 |
| FR08 | TC_RUN_01 |
| FR09 | TC_SUB_01 – TC_SUB_16 |
| FR10 | TC_RANK_01 – TC_RANK_05 |
| FR11 | TC_TEAM_01 – TC_TEAM_18 |
| FR12 | TC_CRUD_01 – TC_CRUD_05 |
| FR13 | TC_CRUD_06 – TC_CRUD_08 |
| FR14 | TC_CRUD_09 – TC_CRUD_11 |
| FR15 | TC_TCM_01 – TC_TCM_10 |
| FR16 | TC_USR_01 – TC_USR_09, TC_CRUD_12 – TC_CRUD_14 |
| NFR01 | TC_NFR_01 |
| NFR02 | TC_NFR_02 |
| NFR03 | TC_NFR_03, TC_CRUD_12, TC_CRUD_13 |
| NFR04 | TC_NFR_04 |
| NFR05 | TC_NFR_05 |

## 19. PHÊ DUYỆT
| Vai trò | Họ tên | Chữ ký | Ngày |
| :--- | :--- | :--- | :--- |
| Người lập | Trần Anh Khoa | | |
| Người duyệt | ThS. Trần Quang Bình | | |
