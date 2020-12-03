async function addGoogleCalendar(courseId){
  let confirmResult = confirm("구글 캘린더에 추가할까요?");
  if(confirmResult){

    const dto = {
      courseId : courseId
    }
    let response = fetch('/mycourse/addGoogleCalendar', {
      method: 'post',
      headers: {
        'Content-Type': 'application/json'
      },
      body: JSON.stringify(dto)
    })
    alert("추가 요청을 보냈습니다.")
  }
}

async function deleteGoogleCalendar(courseId){
  let confirmResult = confirm("구글 캘린더에서 삭제할까요?");
  if(confirmResult){
    const dto = {
      courseId : courseId
    }
    let response = fetch('/mycourse/deleteGoogleCalendar', {
      method: 'post',
      headers: {
        'Content-Type': 'application/json'
      },
      body: JSON.stringify(dto)
    })
    alert("삭제 요청을 보냈습니다.")
  }
}