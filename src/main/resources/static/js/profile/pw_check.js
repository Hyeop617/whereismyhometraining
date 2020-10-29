async function check(){
  const passwordInput = document.querySelector("[name=passwordInput]")

  const response = await (await fetch('/profile/check', {
    method: 'post',
    headers: {
      'Content-Type': 'application/json'
    },
    body : passwordInput.value
  }))
  if(response.status == 200){
    window.location.href = '/profile/edit'
  } else {
    alert("비밀번호를 확인해주세요.")
  }
}