let emailAvailable = true;
const emailReg = /^[0-9a-zA-Z]([-_\.]?[0-9a-zA-Z])*@[0-9a-zA-Z]([-_\.]?[0-9a-zA-Z])*\.[a-zA-Z]{2,3}$/i;

window.onload = function () {
  inputValue();
  if(role == "ROLE_USER") {
    passwordCheck();
    emailCheck();
  }
  nicknameCheck();
  genderCheck();
  ageCheck();
};

function inputValue(){
  const inputAge = document.querySelector('[name=age]')
  const inputGender = document.querySelector('[name=gender]')
  inputAge.value = age
  inputGender.value = gender

}

nicknameCheck = function (){
  const nickname = document.querySelector('[name=nickname]')
  const nicknameAlert = document.querySelector('[name=nicknameAlert]')

  nickname.addEventListener('keyup', function (){
    nicknameAlert.classList.add('d-none');
  })
}

genderCheck = function () {
  const gender = document.querySelector('[name=gender]')
  const genderAlert = document.querySelector('[name=genderAlert]')

  gender.addEventListener('keyup', function (){
    genderAlert.classList.add('d-none');
  })
}

ageCheck = function () {
  const age = document.querySelector('[name=age]')
  const ageAlert = document.querySelector('[name=ageAlert]')

  age.addEventListener('change', function (){
    ageAlert.classList.add('d-none');
  })
}
emailCheck = function () {
  const email = document.querySelector('[name=email]');
  const emailAlert = document.querySelector('[name=emailAlert]');
  const emailSuccess = document.querySelector('[name=emailSuccess]');

  email.addEventListener('focus', function () {
    emailAlert.classList.add('d-none');
  })

  email.addEventListener('blur', function () {
    if(email.value == emailPrev){
      emailAlert.classList.add('d-none');
      emailSuccess.classList.remove('d-none');
      emailAvailable = true;
    }else if(emailReg.test(email.value)){
      emailAlert.innerText = '중복된 이메일 입니다.'
      fetch("/signup/checkEmail", {
        method: 'post',
        headers: {
          'Content-Type': 'application/json'
        },
        body: email.value
      }).then(res => {
        if (res.status === 200) {
          emailAlert.classList.add('d-none');
          emailSuccess.classList.remove('d-none');
          emailAvailable = true;
        } else if (res.status === 409) {
          emailAlert.classList.remove('d-none');
          emailSuccess.classList.add('d-none');
          emailAvailable = false;
        } else {
          console.log(res);
        }
      })
    }else {
      emailAlert.innerText = '이메일 형식을 확인해주세요.';
      emailSuccess.classList.add('d-none');
      emailAlert.classList.remove('d-none');
    }
  })
};

passwordCheck = function () {
  const password1 = document.querySelector('[name=password]');
  const password2 = document.querySelector('[name=password2]');
  const passwordAlert1 = document.querySelector('[name=passwordAlert]');
  const passwordAlert2 = document.querySelector('[name=passwordAlert2]');

  password1.addEventListener('focus', function () {
    passwordAlert1.classList.add('d-none');
  })

  password2.addEventListener('focus', function () {
    passwordAlert2.innerText = '비밀번호가 맞지 않습니다.'
    passwordAlert2.classList.add('d-none');
  })

  password1.addEventListener('keyup', function () {
    if (password1.value !== password2.value && password2.value !== '') {
      passwordAlert2.classList.remove('d-none');
      passwordAvailable = false;
    } else {
      passwordAlert2.classList.add('d-none');
      passwordAvailable = true;
    }
  });

  password2.addEventListener('keyup', function () {
    if (password1.value !== password2.value) {
      passwordAlert2.classList.remove('d-none');
      passwordAvailable = false;
    } else {
      passwordAlert2.classList.add('d-none');
      passwordAvailable = true;
    }
  })
};

validate = function () {
  if(role == "ROLE_USER"){
    passwordValidation()
    emailValidation()
  }
  nicknameValidation()
  genderValidation()
  ageValidation()
  return role == "ROLE_USER" ? emailValidation && nicknameValidation && genderValidation && ageValidation && passwordValidation()
                      : emailValidation && nicknameValidation && genderValidation && ageValidation;
}

emailValidation = function () {
  const email = document.querySelector('[name=email]');
  const emailAlert = document.querySelector('[name=emailAlert]');

  if(email.value == '' && role == "ROLE_USER"){
    emailAlert.innerText = '이메일을 입력해주세요.';
    emailAlert.classList.remove('d-none');
    return false
  }else {
    emailAlert.classList.add('d-none');
    return true
  }
}

passwordValidation = function () {
  const password1 = document.querySelector('[name=password]');
  const password2 = document.querySelector('[name=password2]');
  const passwordAlert1 = document.querySelector('[name=passwordAlert]');
  const passwordAlert2 = document.querySelector('[name=passwordAlert2]');

  if(password1.value == '') {
    passwordAlert1.innerText = '비밀번호를 입력해주세요.'
    passwordAlert1.classList.remove('d-none');
  } else {
    passwordAlert1.classList.add('d-none');
  }

  if(password2.value == '') {
    passwordAlert2.innerText = '비밀번호를 입력해주세요.'
    passwordAlert2.classList.remove('d-none');
  } else {
    passwordAlert2.classList.add('d-none');
  }

  return password1.value != '' && password2.value != '';
}

nicknameValidation = function () {
  const nickname = document.querySelector('[name=nickname]');
  const nicknameAlert = document.querySelector('[name=nicknameAlert]');

  if(nickname.value == ''){
    nicknameAlert.classList.remove('d-none');
    return false;
  }else {
    nicknameAlert.classList.add('d-none');
    return true;
  }
};

genderValidation = function () {
  const gender = document.querySelector('[name=gender]');
  const genderAlert = document.querySelector('[name=genderAlert]')

  if(gender.value == ''){
    genderAlert.classList.remove('d-none');
    return false;
  }else {
    genderAlert.classList.add('d-none');
    return true;
  }
}

ageValidation = function () {
  const age = document.querySelector('[name=age]');
  const ageAlert = document.querySelector('[name=ageAlert]')

  if(age.value == ''){
    ageAlert.classList.remove('d-none');
    return false;
  }else {
    ageAlert.classList.add('d-none');
    return true;
  }
}

async function edit() {
  if(validate()) {
    if(emailAvailable && passwordAvailable) {
      const account = {
        nickname: document.querySelector('[name=nickname]').value,
        gender: document.querySelector('[name=gender]').value == 'M' ? "M" : "F",
        age: document.querySelector('[name=age]').value,
      };
      if(role == "ROLE_USER"){
        account.password = document.querySelector('[name=password]').value
        account.email = document.querySelector('[name=email]').value
      }
      let response = await (await fetch("/profile/edit", {
        method: 'post',
        headers: {
          'Content-Type': 'application/json'
        },
        body: JSON.stringify(account)
      }));
      console.log(response)
      alert("변경되었습니다")
      history.go(-2 )

    } else {
      alert("필수 항목값을 확인해주세요.")
    }
  } else {
    alert("필수 항목값을 입력해주세요")
  }
}
