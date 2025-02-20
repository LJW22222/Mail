function deleteSurvey(fileName) {
    if (confirm("설문조사를 삭제하시겠습니까?")) {
        $.ajax({
            url: "/surveys/deleteSurvey/" + encodeURIComponent(fileName),
            type: 'DELETE',
            success: function (response) {
                alert('설문조사가 성공적으로 삭제되었습니다.');
                // 삭제 후 필요한 작업 수행 (예: 페이지 새로고침 등)
            },
            error: function () {
                alert('설문조사 삭제 중 오류가 발생했습니다.');
            }
        });
    }
}
