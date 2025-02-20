$(document).ready(function () {
    function addField(html) {
        var fieldContainer = $('<div class="field-container"></div>').append(html);
        fieldContainer.append('<span class="drag-handle">&#x22EE;</span>');
        $('#survey-form').append(fieldContainer);
        $('#survey-form').sortable({
            handle: '.drag-handle',
            containment: 'parent'
        });
    }

    function addFieldWithEditableTitle(title, html) {
        var fieldGroup = $('<div class="field-group"></div>');
        var fieldTitle = $('<h3 class="field-title">' + escapeHtml(title) + '</h3>');

        // 실시간으로 title 변경에 따라 다른 필드의 name 속성 업데이트
        fieldTitle.on('input', function () {
            var newValue = $(this).val();
            var escapedValue = escapeHtml(newValue);

            // field-title의 value와 name을 업데이트
            $(this).attr('value', escapedValue);
            $(this).attr('name', escapedValue);

            // 같은 field-group 내의 다른 필드들도 업데이트
            $(this).closest('.field-group').find(':input').not('.field-title').each(function () {
                $(this).attr('name', escapedValue);
            });
        });

        fieldGroup.append(fieldTitle);
        fieldGroup.append(html);
        addField(fieldGroup);
    }

    $('.add-text-field').click(function () {
        addFieldWithEditableTitle('텍스트 필드 제목', '<input type="text" name="텍스트 필드 제목" placeholder="텍스트 필드">');
    });

    $('.add-select-field').click(function () {
        var selectOptions = prompt("선택 옵션을 입력하세요 (콤마로 구분하여 여러 옵션 입력 가능):");
        if (selectOptions !== null) {
            var options = selectOptions.split(',');
            var selectField = '<select name="선택 옵션 필드 제목">';
            options.forEach(function (option) {
                selectField += '<option value="' + escapeHtml(option.trim()) + '">' + escapeHtml(option.trim()) + '</option>';
            });
            selectField += '</select>';
            addFieldWithEditableTitle('선택 옵션 필드 제목', selectField);
        }
    });

    $('.add-textarea').click(function () {
        addFieldWithEditableTitle('텍스트 에어리어 제목', '<textarea name="텍스트 에어리어 제목" placeholder="텍스트 에어리어"></textarea>');
    });

    $('.add-checkbox-field').click(function () {
        var checkOptions = prompt("체크 박스 옵션을 입력하세요 (콤마로 구분하여 여러 옵션 입력 가능):");
        if (checkOptions !== null) {
            var options = checkOptions.split(',');
            var checkField = '';
            options.forEach(function (option) {
                checkField += '<input type="checkbox" name="체크박스 필드 제목" value="' + escapeHtml(option.trim()) + '"><label>' + escapeHtml(option.trim()) + '</label>';
            });
            addFieldWithEditableTitle('체크박스 필드 제목', checkField);
        }
    });

    $('.add-radio-field').click(function () {
        var radioOptions = prompt("라디오 버튼 옵션을 입력하세요 (콤마로 구분하여 여러 옵션 입력 가능):");
        if (radioOptions !== null) {
            var options = radioOptions.split(',');
            var radioField = '';
            options.forEach(function (option) {
                radioField += '<input type="radio" name="라디오 버튼 제목" value="' + escapeHtml(option.trim()) + '"><label>' + escapeHtml(option.trim()) + '</label>';
            });
            addFieldWithEditableTitle('라디오 버튼 제목', radioField);
        }
    });

    $('.save-button').click(function () {
        var surveyName = prompt("설문조사의 이름을 입력하세요:");
        if (surveyName !== null && surveyName.trim() !== "") {
            // $('.field-title').prop('disabled', true);
            $('label').addClass('disabled');
            $('input[type="text"]').prop('disabled', true);
            $('#survey-form').sortable('disable');

            var surveyHTML = $('#survey-form').html();

            $.ajax({
                type: 'POST',
                url: '/surveys/saveSurvey',
                contentType: 'application/json',
                data: JSON.stringify({
                    name: surveyName + ".html",
                    content: "<!DOCTYPE html>\n" +
                        "<html lang=\"en\" xmlns:th=\"http://www.thymeleaf.org\">\n" +
                        "<head>\n" +
                        "    <meta charset=\"UTF-8\">\n" +
                        "    <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">\n" +
                        "    <title>" + escapeHtml(surveyName) + "</title>\n" +
                        "    <link rel=\"stylesheet\" href=\"/css/survey.css\" />\n" +
                        "</head>\n" +
                        "<body>\n" +
                        "<div class=\"main-content\">\n" +
                        "<form id=\"managementForm\" method=\"post\" action=\"/sendMail\">\n" +
                        surveyHTML +
                        "    <div class=\"form-group\">\n" +
                        "        <button id=\"submitButton\" type=\"submit\">제출</button>\n" +
                        "    </div>\n" +
                        "</form>\n" +
                        "    </div>" +
                        "</body>\n" +
                        "</html>"
                }),
                success: function (response) {
                    alert(response);
                },
                error: function () {
                    alert('저장에 실패했습니다.');
                },
                complete: function () {
                    // $('.field-title').prop('disabled', true);
                    $('label').addClass('disabled');
                    $('input[type="text"]').prop('disabled', true);
                    $('#survey-form').sortable('disable');
                }
            });
        } else {
            alert("이름을 입력해야 합니다.");
        }
    });

    function escapeHtml(string) {
        return string.replace(/[&<>"'`=\/]/g, function (s) {
            return entityMap[s];
        });
    }

    var entityMap = {
        '&': '&amp;',
        '<': '&lt;',
        '>': '&gt;',
        '"': '&quot;',
        "'": '&#39;',
        '/': '&#x2F;',
        '`': '&#x60;',
        '=': '&#x3D;'
    };

    // title 입력 필드의 값 실시간 업데이트
    $('#title-input').on('input', function() {
        var newValue = $(this).val();
        $(this).attr('value', newValue);
    });
});
