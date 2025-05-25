### API 명세서
| 기능       | Method | URL                            | Request Body / Query                                                              | Response                | HTTP Status |
| -------- | ------ | ------------------------------ | --------------------------------------------------------------------------------- | ----------------------- | ----------- |
| 일정 등록    | POST   | `/schedules`                   | `{ "todo": "...", "writerName": "...", "writerEmail": "...", "password": "..." }` | 생성된 일정 정보               | 200 OK      |
| 전체 일정 조회 | GET    | `/schedules?page=1&size=10`    | `page`, `size` (쿼리 파라미터)                                                          | `[ScheduleResponseDto]` | 200 OK      |
| 단건 일정 조회 | GET    | `/schedules/{id}`              | Path Variable (`id`)                                                              | 일정 1건 정보                | 200 OK      |
| 일정 내용 수정 | PATCH  | `/schedules/{id}/todo`         | 쿼리: `?todo=...&password=...`                                                      | 수정된 일정 정보               | 200 OK      |
| 작성자 변경   | PATCH  | `/schedules/{id}/writer`       | `{ "writerName": "...", "writerEmail": "...", "password": "..." }`                | 수정된 일정 정보               | 200 OK      |
| 일정 삭제    | DELETE | `/schedules/{id}?password=...` | 쿼리: `password`                                                                    | 없음 (204 No Content)     | 200 OK        |

 
### ERD
<pre>writer
├── id (PK) : BIGINT
├── name : VARCHAR(100)
├── email : VARCHAR(255)
├── created_at : DATETIME
└── modified_at : DATETIME

schedule
├── id (PK) : BIGINT
├── todo : VARCHAR(200)
├── password : VARCHAR(255)
├── writer_id (FK → writer.id) : BIGINT
├── created_at : DATETIME
└── modified_at : DATETIME</pre>
