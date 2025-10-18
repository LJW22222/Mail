package server.mail.infra.mail;

import server.mail.application.mail.dto.Answer;
import server.mail.domain.survey.Survey;
import server.mail.domain.survey.vo.Field;

import java.util.*;
import java.util.stream.Collectors;

public class BuildEmailHtml {

    public static String buildContactHtml(String name, String subject, String message, String email) {
        final String EMPTY = "<span style='color:#9ca3af'>—</span>";

        String n = (name == null || name.isBlank()) ? EMPTY : safe(name);
        String s = (subject == null || subject.isBlank()) ? EMPTY : safe(subject);
        String m = (message == null || message.isBlank()) ? EMPTY : safe(message).replace("\n", "<br/>");

        // mailto 링크는 따옴표/꺽쇠 등만 적당히 이스케이프되면 충분 (safe를 사용)
        String emailDisplay = (email == null || email.isBlank()) ? EMPTY : safe(email);
        String emailCell = (email == null || email.isBlank())
                ? EMPTY
                : "<a href='mailto:" + safe(email) + "' style='color:#0ea5e9;text-decoration:none'>" + emailDisplay + "</a>";

        String nowKST = java.time.ZonedDateTime.now(java.time.ZoneId.of("Asia/Seoul"))
                .format(java.time.format.DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss z"));

        String html = """
                <div style="font-family: system-ui, -apple-system, Segoe UI, Roboto, 'Noto Sans KR', Arial; line-height:1.65; color:#111827;">
                  <div style="max-width:720px;margin:0 auto;padding:18px;">
                    <div style="border:1px solid #e5e7eb;border-radius:16px;overflow:hidden;box-shadow:0 4px 14px rgba(17,24,39,0.06);">
                
                      <!-- 헤더 -->
                      <div style="background:linear-gradient(135deg,#06b6d4,#0ea5e9);color:#fff;padding:22px 26px;">
                        <div style="font-size:13px;opacity:.9;letter-spacing:.3px;">CONTACT FORM</div>
                        <div style="font-size:22px;font-weight:800;margin-top:4px;">새 문의가 도착했어요</div>
                      </div>
                
                      <!-- 본문 -->
                      <div style="padding:22px 26px;background:#ffffff;">
                
                        <!-- 요약 배지 -->
                        <div style="display:inline-block;background:#f1f5f9;color:#0f172a;border:1px solid #e2e8f0;border-radius:999px;padding:6px 12px;font-size:12px;font-weight:600;margin-bottom:14px;">
                          문의 요약
                        </div>
                
                        <!-- 메타 정보 테이블 -->
                        <table style="width:100%%;border-collapse:collapse;border:1px solid #e5e7eb;border-radius:12px;overflow:hidden;">
                          <thead>
                            <tr>
                              <th style="text-align:left;background:#f8fafc;border-bottom:1px solid #e5e7eb;padding:12px 14px;width:28%%;font-size:13px;color:#334155;">항목</th>
                              <th style="text-align:left;background:#f8fafc;border-bottom:1px solid #e5e7eb;padding:12px 14px;font-size:13px;color:#334155;">내용</th>
                            </tr>
                          </thead>
                          <tbody style="font-size:14px;">
                            <tr>
                              <td style="border-top:1px solid #e5e7eb;padding:12px 14px;color:#1f2937;">보낸 사람</td>
                              <td style="border-top:1px solid #e5e7eb;padding:12px 14px;">%s</td>
                            </tr>
                            <tr>
                              <td style="border-top:1px solid #e5e7eb;padding:12px 14px;color:#1f2937;">이메일</td>
                              <td style="border-top:1px solid #e5e7eb;padding:12px 14px;">%s</td>
                            </tr>
                            <tr>
                              <td style="border-top:1px solid #e5e7eb;padding:12px 14px;color:#1f2937;">제목</td>
                              <td style="border-top:1px solid #e5e7eb;padding:12px 14px;">%s</td>
                            </tr>
                          </tbody>
                        </table>
                
                        <!-- 메시지 박스 -->
                        <div style="margin-top:18px;border:1px solid #e5e7eb;border-radius:12px;background:#f8fafc;">
                          <div style="padding:12px 14px;border-bottom:1px solid #e5e7eb;font-weight:700;color:#0f172a;">메시지</div>
                          <div style="padding:14px 16px;color:#374151;word-break:break-word;">%s</div>
                        </div>
                
                        <!-- 액션/푸터 -->
                        <div style="display:flex;justify-content:space-between;align-items:center;gap:10px;margin-top:16px;flex-wrap:wrap;">
                          <div style="color:#64748b;font-size:12px;">
                            수신 시각: %s
                          </div>
                          %s
                        </div>
                
                      </div>
                    </div>
                
                    <!-- 하단 안내 -->
                    <div style="text-align:center;color:#94a3b8;font-size:12px;margin-top:14px;">
                      이 메일은 문의 알림을 위해 발송되었습니다. 회신이 필요 없다면 무시하셔도 됩니다.
                    </div>
                  </div>
                </div>
                """;

        // 우측에 "바로 답장" 버튼(이메일이 있을 때만)
        String cta = (email == null || email.isBlank())
                ? "<div style='height:32px'></div>"
                : """
                <a href="mailto:%s" style="display:inline-block;padding:8px 14px;border:1px solid #0ea5e9;border-radius:10px;text-decoration:none;font-size:13px;font-weight:700;">
                  <span style="color:#0ea5e9;">바로 답장하기 ↗</span>
                </a>
                """.formatted(safe(email));

        return html.formatted(n, emailCell, s, m, nowKST, cta);
    }


    /**
     * 메일 본문 HTML 생성 (Q → A 카드형)
     */
    public static String buildEmailHtml(Survey survey, List<Answer> answers) {
        Map<String, Field> fieldMap = Optional.ofNullable(survey.getFields()).orElse(List.of())
                .stream()
                .collect(Collectors.toMap(Field::id, f -> f, (a, b) -> a));

        // 구분선 제외 + level 순 정렬
        List<Answer> viewAnswers = Optional.ofNullable(answers).orElse(List.of())
                .stream()
                .filter(a -> {
                    Field f = fieldMap.get(a.fieldId());
                    return f == null || f.type() != server.mail.domain.survey.vo.QuestionType.DIVIDER;
                })
                .sorted(Comparator.comparingInt(a -> {
                    Field f = fieldMap.get(a.fieldId());
                    return f != null && f.level() != null ? f.level() : Integer.MAX_VALUE;
                }))
                .toList();

        String nowKST = java.time.ZonedDateTime.now(java.time.ZoneId.of("Asia/Seoul"))
                .format(java.time.format.DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss z"));

        String title = safe(Optional.ofNullable(survey.getTitle()).orElse("무제 설문"));
        String desc  = safe(Optional.ofNullable(survey.getDescription()).orElse(""));

        StringBuilder sb = new StringBuilder();

        // 헤더
        sb.append("""
      <div style="font-family: system-ui, -apple-system, Segoe UI, Roboto, 'Noto Sans KR', Arial; line-height:1.65; color:#111827;">
        <div style="max-width:860px;margin:0 auto;padding:18px;">
          <div style="border:1px solid #e5e7eb;border-radius:16px;overflow:hidden;box-shadow:0 6px 18px rgba(17,24,39,0.08);">
            <div style="background:linear-gradient(135deg,#06b6d4,#0ea5e9);color:#fff;padding:22px 26px;">
              <div style="font-size:13px;opacity:.9;letter-spacing:.3px;">SURVEY RESPONSES</div>
              <div style="font-size:22px;font-weight:800;margin-top:4px;">%s</div>
            </div>
            <div style="padding:22px 26px;background:#ffffff;">
    """.formatted(title));

        // 설명 콜아웃
        if (!desc.isBlank()) {
            sb.append("""
          <div style="border:1px solid #e5e7eb;background:#f8fafc;color:#334155;border-radius:12px;padding:14px 16px;margin:0 0 16px 0;">
            <div style="font-weight:700;margin-bottom:6px;">설문 설명</div>
            <div>%s</div>
          </div>
        """.formatted(desc));
        }

        // Q → A 카드형 목록
        if (viewAnswers.isEmpty()) {
            sb.append("""
          <div style="border:1px dashed #e5e7eb;border-radius:12px;padding:18px;text-align:center;color:#64748b;">
            아직 표시할 응답이 없습니다.
          </div>
        """);
        } else {
            for (Answer a : viewAnswers) {
                Field f = fieldMap.get(a.fieldId());
                String q = (f != null && f.label() != null) ? f.label() : a.fieldId();
                String qSafe = safe(q);
                String requiredBadge = (f != null && Boolean.TRUE.equals(f.required()))
                        ? " <span style='color:#ef4444;font-size:12px;border:1px solid #fecaca;background:#fee2e2;border-radius:999px;padding:1px 8px;margin-left:6px;'>필수</span>"
                        : "";
                String answerHtml = renderAnswerForMailPretty(f, a.value()); // 칩/별점/링크 처리 버전

                sb.append("""
              <div style="border:1px solid #e5e7eb;border-radius:12px;background:#ffffff;margin:0 0 12px 0;">
                <div style="padding:12px 14px 6px 14px;font-weight:700;color:#0f172a;">
                  %s%s
                </div>
                <div style="padding:12px 14px 14px 14px;">
                  <div style="border:1px solid #eef2f7;background:#f8fafc;border-radius:10px;padding:12px;color:#374151;word-break:break-word;">
                    %s
                  </div>
                </div>
              </div>
            """.formatted(qSafe, requiredBadge, answerHtml));
            }
        }

        // 푸터(전송 시각)
        sb.append("""
            <div style="display:flex;justify-content:space-between;align-items:center;gap:10px;margin-top:6px;flex-wrap:wrap;">
              <div style="color:#64748b;font-size:12px;">전송 시각: %s</div>
              <div></div>
            </div>
          </div>
        </div>
        <div style="text-align:center;color:#94a3b8;font-size:12px;margin-top:14px;">
          이 메일은 설문 응답 알림을 위해 발송되었습니다.
        </div>
      </div>
    """.formatted(nowKST));

        return sb.toString();
    }


    /**
     * 메일 응답 렌더링 (칩/링크/별점 등 미려하게)
     */
    private static String renderAnswerForMailPretty(server.mail.domain.survey.vo.Field field, Object value) {
        if (value == null) return "<span style='color:#9ca3af'>—</span>";

        // 배열/리스트 응답 → 칩 형태로
        if (value instanceof String[] arr) {
            return Arrays.stream(arr)
                    .map(BuildEmailHtml::safe)
                    .map(v -> "<span style='display:inline-block;border:1px solid #e2e8f0;background:#f8fafc;border-radius:999px;padding:3px 10px;margin:2px 6px 2px 0;font-size:12px;color:#0f172a;'>"+ v +"</span>")
                    .collect(Collectors.joining());
        }
        if (value instanceof Collection<?> col) {
            if (col.isEmpty()) return "<span style='color:#9ca3af'>—</span>";
            return col.stream()
                    .map(String::valueOf)
                    .map(BuildEmailHtml::safe)
                    .map(v -> "<span style='display:inline-block;border:1px solid #e2e8f0;background:#f8fafc;border-radius:999px;padding:3px 10px;margin:2px 6px 2px 0;font-size:12px;color:#0f172a;'>"+ v +"</span>")
                    .collect(Collectors.joining());
        }

        // 숫자/별점 표현
        if (value instanceof Number n) {
            if (field != null && field.type() == server.mail.domain.survey.vo.QuestionType.RATING) {
                int max = field.max() != null ? field.max() : 5;
                int v = n.intValue();
                String stars = "★".repeat(Math.max(0, Math.min(v, max))) +
                        "<span style='color:#d1d5db'>" + "★".repeat(Math.max(0, max - v)) + "</span>";
                return stars + " <span style='color:#6b7280'>(" + v + "/" + max + ")</span>";
            }
            return safe(n.toString());
        }

        // 날짜형은 그대로
        if (field != null && field.type() == server.mail.domain.survey.vo.QuestionType.DATE) {
            return safe(String.valueOf(value));
        }

        // URL 자동 링크 (아주 단순한 패턴)
        String s = String.valueOf(value);
        String trimmed = s.trim();
        if ((trimmed.startsWith("http://") || trimmed.startsWith("https://")) && !trimmed.contains(" ")) {
            String esc = safe(trimmed);
            return "<a href='" + esc + "' style='color:#0ea5e9;text-decoration:none'>" + esc + "</a>";
        }

        // 일반 텍스트: 줄바꿈 유지
        return safe(s).replace("\n", "<br/>");
    }


    public static String safe(String s) {
        if (s == null) return "";
        return s.replace("&", "&amp;")
                .replace("<", "&lt;")
                .replace(">", "&gt;")
                .replace("\"", "&quot;")
                .replace("'", "&#39;");
    }

}
