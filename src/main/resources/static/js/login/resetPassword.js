async function change(){
  const password1 = document.querySelector('[name=password]');
  const password2 = document.querySelector('[name=password2]');
  if(password1.value == '' || password2.value == ''){
    alert("비밀번호를 입력해주세요.")
  } else if (password1.value != password2.value){
    alert("비밀번호가 맞지 않습니다.")
  } else {
    const dto = {
      password : password1.value,
      token : token
    }
    const response = await (await fetch("/login/reset", {
      method: 'post',
      headers: {
        'Content-Type': 'application/json'
      },
      body: JSON.stringify(dto)
    })).json()
    console.log(response)
    if(response.data == "done"){
      alert("비밀번호가 변경되었습니다.")
      window.location.href = "/login"
    }
  }
}