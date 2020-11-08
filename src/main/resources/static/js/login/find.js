let verificationIdResend = false;
let verificationPwResend = false;

async function findId() {
  const findIdEmail = document.querySelector("[name=findIdEmail]")
  const idVerificationInput = document.querySelector("[name=idVerificationInput]")
  const idVerificationButton = document.querySelector("[name=idVerificationButton]")
  const reqIdVerificationCode = document.querySelector("[name=reqIdVerificationCode]")
  verificationIdResend = true;

  if(verificationIdResend){
    reqIdVerificationCode.textContent = '인증번호 재전송'
  }

  const dto = {
    findPassword : false,
    email : findIdEmail.value,
    username : ''
  }

  const response = await (await fetch("/login/find/sendVerificationCode", {
    method: 'post',
    headers: {
      'Content-Type': 'application/json'
    },
    body: JSON.stringify(dto)
  })).json()

  if(response.data == 'not found'){
    alert("해당하는 계정이 없습니다.")
    history.go(0);
  } else {
    idVerificationInput.disabled = false;
    idVerificationButton.classList.remove("disabled");

    timer(idVerificationButton, idVerificationInput);
  }
}

async function findPw() {
  const findPwEmail = document.querySelector("[name=findPwEmail]")
  const findPwUsername = document.querySelector("[name=findPwUsername]")
  const PwVerificationInput = document.querySelector("[name=PwVerificationInput]")
  const PwVerificationButton = document.querySelector("[name=PwVerificationButton]")
  const reqPwVerificationCode = document.querySelector("[name=reqPwVerificationCode]")
  verificationPwResend = true;

  if(verificationPwResend){
    reqPwVerificationCode.textContent = '인증번호 재전송'
  }

  const dto = {
    findPassword : true,
    email : findPwEmail.value,
    username : findPwUsername.value
  }

  const response = await (await fetch("/login/find/sendVerificationCode", {
    method: 'post',
    headers: {
      'Content-Type': 'application/json'
    },
    body: JSON.stringify(dto)
  })).json()

  if(response.data == 'not found'){
    alert("해당하는 계정이 없습니다.")
    history.go(0);
  } else {
    PwVerificationInput.disabled = false;
    PwVerificationButton.classList.remove("disabled");

    timer(PwVerificationButton, PwVerificationInput);
  }
}

async function checkVerificationCode(findPassword, code, email){
  //totoll13mail@gmail.com
  const dto = {
    findPassword : findPassword,
    code : code,
    email : email
  }
  const response = await (await fetch("/login/find/checkVerificationCode", {
    method: 'post',
    headers: {
      'Content-Type': 'application/json'
    },
    body: JSON.stringify(dto)
  })).json()
  console.log(response);
  if(response.data.findPassword == true){
    document.location.href = `/login/reset?token=${response.data.redirectPasswordUri}`
  } else if (response.data.findPassword == false){
    alert(`회원님의 아이디는 ${response.data.username} 입니다.`)
    history.go(0);
  }
}


function timer(VerificationButton, VerificationInput) {
  console.log(VerificationButton)
  console.log(VerificationInput)
  VerificationInput.placeholder = "인증번호를 3분 이내에 입력 후 오른쪽 버튼을 눌러주세요"
  let time = 180;
  let interval = setInterval(function () {
    time--;
    VerificationButton.textContent = `${parseInt(time / 60)}:${time % 60}`
    if(time < 0){
      VerificationButton.textContent = '인증시간 만료'
      clearInterval(interval)
    }
  }, 1000);
  console.log('kkkk')

}