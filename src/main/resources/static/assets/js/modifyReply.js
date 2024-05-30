// 수정 이벤트 등록 함수
export function modifyReplyClickEvent() {

  document.getElementById('replyData').addEventListener('click', e => {
    e.preventDefault();

    if (!e.target.matches('#replyModBtn')) return;

    console.log(e.target.closest('.row').firstElementChild.textContent);

    // 수정 전 텍스트 읽기
    const replyText = e.target.closest('.row').firstElementChild.textContent;

    // 모달의 textArea에 넣기
    document.getElementById('modReplyText').value = replyText;

  });
}