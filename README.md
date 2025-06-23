
# Bài tap ve nha _ NHOM2

## Câu 1 . Tiêu đề 
**Quản lý cửa hàng xe**

## Câu 2 . Phân tích cơ sở ít nhất 03 đối tượng
1. **Khách hàng**: quản lý thông tin khách mua xe gồm : mã đơn, tên khách, số điện thoại , mã xe .

2. **Sản phẩm (Xe)**: quản lý các sản phẩm có trong cửa hàng : mã sản phẩm, tên , hãng , giá , số lượng trong kho , tình trang (còn /hết) .

3. **Đơn đặt cọc**: quản lý các đơn đặt xe + đặt cọc : mã đơn, thông tin khách , sản phẩm , ngày đặt , ngày hẹn lấy , tiền cọc , trạng thái đặt (đã /chưa).

## Câu 3 . xây dựng cấu trúc folder của Project
 Đã hoàn thành 

## Câu 4. Các class đã tạo:
1. KhachHang.java
2.  Xe.java
3.  DonDatCoc.java
4. KhachHangTest.java
5. XeTest.java
6.  DonDatCocTest.java

## Câu 5. Các class kiểm định:
- KhachHangTest.java
- XeTest.java
- DonDatCocTest.java


# Bài tap UML
- Thành viên :
1. Nguyễn Thị Huyền Trang
ID : 23010181
chữ ký : Trang

2. Đặng Đắc Tú
ID : 23010619
chữ kí : Tú

3. Lưu Quang Dũng
ID : 23010665
chữ kí : Dũng



Project_nhóm-02:
Xây dựng ứng dụng quản lý cửa hàng xe.

Yêu cầu chính:
- Giao diện <b>Java Spring Boot</b>.
- Có chức năng chính :
+ Quản lý khách hàng, xe, và đơn đặt cọc.
+ Và các chức năng khác.

Cụ thể:

- Quản lý Khách hàng
+ chức năng: Thêm, sửa, xóa thông tin khách hàng.
+ Liệt kê: Hiển thị danh sách khách hàng, tên khách hàng.

- Quản lý Xe
+ Chức năng: Thêm, sửa, xóa thông tin xe (bao gồm mã xe, tên xe, hãng xe, giá, số lượng, trạng thái).
+ Liệt kê: Hiển thị danh sách xe, có thể lọc theo hãng xe hoặc trạng thái (còn/hết hàng).

- Quản lý Đơn đặt cọc
+ Chức năng: Thêm, sửa, xóa đơn đặt cọc.
+ Gán khách hàng cho xe: Cho phép gán một khách hàng cụ thể cho một xe trong đơn đặt cọc.

- Dữ liệu được lưu trữ dưới dạng file nhị phân.
+ Các lớp liên quan (KhachHang, Xe, DonDatCoc) được thiết kế để đọc và ghi dữ liệu từ/đến một hoặc nhiều file nhị phân.

- Trong bộ nhớ, dữ liệu được lưu trữ dưới dạng Collection (ví dụ: ArrayList cho danh sách khách hàng/xe, Map để ánh xạ khách hàng với đơn đặt cọc).
- Chức năng bổ sung (tùy chọn):
+ Tìm kiếm xe theo giá hoặc số lượng.
+ Thống kê số lượng đơn đặt cọc theo trạng thái (đã hoàn thành/chưa hoàn thành).
- Sơ đồ Behavioural Diagram của bài tập lớn
+ Sơ đồ quản lý 
 ![image](https://github.com/user-attachments/assets/f5607d0c-6e68-48ce-8bba-788133196c7d)
+ Sơ đồ chức năng quản lý khách hàng
  ![image](https://github.com/user-attachments/assets/b47e043e-c586-49e9-afb4-8af6941a7d3d)
+ Sơ đồ chức năng đơn đặt cọc
 ![image](https://github.com/user-attachments/assets/892ccaf8-d9e3-4c9e-be22-36c6aad43adf)
+ Sơ đồ chức năng quản lí xe
 ![image](https://github.com/user-attachments/assets/018d0f65-50fc-431f-9915-fa6b340c4fbd)


- Nội dung 04:
Cập nhật code cho CRUD (create/read/update/delete)  và chạy kiểm định (test) cho ít nhất 03 đối tượng trong bài tập lớn (đã hoàn thành)

![image](https://github.com/user-attachments/assets/179cfcee-a8bd-4701-8392-14fbb1b82858)



Practice 6: Yêu cầu số 5 (của bài tập lớn)
Câu  số 1. Các thành viên trong nhóm thảo luận 01 chức năng [phương thức] hoạt động chính [main] của  bài tập cuối kỳ. Sau đó, thực hiện thảo luận nhóm và vẽ lưu đồ thuật toán cho chức năng này. 
+ Chức năng hay phương thức hoạt động này phải là nội dung cốt lõi của ứng dụng của Nhóm  : Chức năng này cho phép nhân viên cửa hàng thực hiện quy trình bán xe, từ việc tạo đơn hàng (khi khách hàng đặt cọc), kiểm tra xe có sẵn để giao, đến cập nhật trạng thái đơn hàng thành "completed" khi giao xe thành công. Chức năng này liên quan đến cả Car, Customer, và Orders, là cốt lõi của ứng dụng quản lý cửa hàng xe của nhóm .

Phân tích thuật toán thành các chức năng nhỏ : 

 + Kiểm tra xe có sẵn để bán: Kiểm tra xem xe (dựa trên ID xe) có trạng thái "conhang" và số lượng đủ để giao hay không.
 + Tạo đơn hàng mới: Tạo một đơn hàng với thông tin khách hàng, xe, ngày đặt cọc, và số tiền đặt cọc.
 + Giao xe và cập nhật trạng thái đơn hàng: Khi giao xe thành công, cập nhật trạng thái đơn hàng thành "completed", giảm số lượng xe trong kho, và ghi nhận thời gian giao xe.


 phân công : 
 - Trang :  Kiểm tra xe có sẵn để bán: Kiểm tra xem xe (dựa trên ID xe) có trạng thái "conhang" và số lượng đủ để giao hay không.
 - Tú :  Tạo đơn hàng mới: Tạo một đơn hàng với thông tin khách hàng, xe, ngày đặt cọc, và số tiền đặt cọc.
 - Dũng :  Giao xe và cập nhật trạng thái đơn hàng: Khi giao xe thành công, cập nhật trạng thái đơn hàng thành "completed", giảm số lượng xe trong kho, và ghi nhận thời gian giao xe.




