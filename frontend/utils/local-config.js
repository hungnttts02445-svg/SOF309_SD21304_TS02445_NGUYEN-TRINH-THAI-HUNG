// Cấu hình chạy LOCAL (branch feature/local-setup)
// File này được nạp ngay sau constants.js (xem core.js) và ghi đè API_BASE
// để frontend luôn gọi backend Tomcat trên máy mình, không qua devclimb.online.
// Dùng cùng hostname với trang đang mở (localhost hoặc 127.0.0.1) để session
// cookie được gửi kèm (cùng site). Đổi port nếu Tomcat chạy port khác 8080.
var API_BASE = window.location.protocol + '//' + window.location.hostname
  + ':8080/devclimb-backend/api/v1';
