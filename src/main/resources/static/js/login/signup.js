let emailAvailable = true;
let usernameAvailable = true;
let passwordAvailable = true;
const emailReg = /^[0-9a-zA-Z]([-_\.]?[0-9a-zA-Z])*@[0-9a-zA-Z]([-_\.]?[0-9a-zA-Z])*\.[a-zA-Z]{2,3}$/i;

window.onload = function () {
    usernameCheck();
    emailCheck();
    passwordCheck();
    nicknameCheck();
    genderCheck();
    ageCheck();
    levelCheck();
    shapeCheck();
    heightCheck();
    weightCheck();
};

function heightCheck(){
    const height = document.querySelector('[name=height]')
    const heightAlert = document.querySelector('[name=heightAlert]')

    height.addEventListener('keyup', function (){
        heightAlert.classList.add('d-none');
    })
}

function weightCheck(){
    const weight = document.querySelector('[name=weight]')
    const weightAlert = document.querySelector('[name=weightAlert]')

    weight.addEventListener('keyup', function (){
        weightAlert.classList.add('d-none');
    })
}

function shapeCheck(){
    const upperLevel = document.querySelector('[name=upperLevel]')
    const upperAlert = document.querySelector('[name=upperAlert]')
    const coreLevel = document.querySelector('[name=coreLevel]')
    const coreAlert = document.querySelector('[name=coreAlert]')
    const lowerLevel = document.querySelector('[name=lowerLevel]')
    const lowerAlert = document.querySelector('[name=lowerAlert]')

    upperLevel.addEventListener('focus', function (){
        upperAlert.classList.add('d-none');
    })

    coreLevel.addEventListener('focus', function (){
        coreAlert.classList.add('d-none');
    })

    lowerLevel.addEventListener('focus', function (){
        lowerAlert.classList.add('d-none');
    })
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

levelCheck = function () {
    const level = document.querySelector('[name=level]')
    const levelAlert = document.querySelector('[name=levelAlert]')

    level.addEventListener('focus', function (){
        levelAlert.classList.add('d-none');
    })
}

usernameCheck = function (){
    const username = document.querySelector('[name=username]');
    const usernameAlert = document.querySelector('[name=usernameAlert]');
    const usernameSuccess = document.querySelector('[name=usernameSuccess]');

    username.addEventListener('focus', function () {
        usernameAlert.classList.add('d-none');
    })

    username.addEventListener('blur', function (){
        usernameAlert.innerText = '중복된 아이디 입니다.'
        fetch("/signup/checkUsername", {
            method: 'post',
            headers: {
                'Content-Type': 'application/json'
            },
            body: username.value
        }).then(res => {
            if (res.status === 200) {
                usernameAlert.classList.add('d-none');
                usernameSuccess.classList.remove('d-none');
                usernameAvailable = true;
            } else if (res.status === 409) {
                usernameAlert.classList.remove('d-none');
                usernameSuccess.classList.add('d-none');
                usernameAvailable = false;
            } else {
                console.log(res);
            }
        })
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
        if(emailReg.test(email.value)){
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
    usernameValidation()
    emailValidation()
    passwordValidation()
    nicknameValidation()
    genderValidation()
    ageValidation()
    levelValidation()
    shapeValidation()
    heightValidation()
    weightValidation()
    return usernameValidation && emailValidation && passwordValidation && nicknameValidation()
            && genderValidation && ageValidation && levelValidation() && shapeValidation() && heightValidation() && weightValidation();
}

function heightValidation() {
    const height = document.querySelector('[name=height]')
    const heightAlert = document.querySelector('[name=heightAlert]')

    if(height.value == ''){
        heightAlert.innerText = '키를 입력해주세요.';
        heightAlert.classList.remove('d-none');
        return false
    }else {
        heightAlert.classList.add('d-none');
        return true
    }
}

function weightValidation() {
    const weight = document.querySelector('[name=weight]')
    const weightAlert = document.querySelector('[name=weightAlert]')

    if(weight.value == ''){
        weightAlert.innerText = '몸무게를 입력해주세요.';
        weightAlert.classList.remove('d-none');
        return false
    }else {
        weightAlert.classList.add('d-none');
        return true
    }
}

function shapeValidation(){
    const upperLevel = document.querySelector('[name=upperLevel]')
    const upperAlert = document.querySelector('[name=upperAlert]')
    const coreLevel = document.querySelector('[name=coreLevel]')
    const coreAlert = document.querySelector('[name=coreAlert]')
    const lowerLevel = document.querySelector('[name=lowerLevel]')
    const lowerAlert = document.querySelector('[name=lowerAlert]')
    let upperBlank = true
    let coreBlank = true
    let lowerBlank = true

    if(upperLevel.value == ''){
        upperAlert.classList.remove('d-none');
        upperBlank = true
    }else {
        upperAlert.classList.add('d-none');
        upperBlank = false
    }

    if(coreLevel.value == ''){
        coreAlert.classList.remove('d-none');
        coreBlank = true
    }else {
        upperAlert.classList.add('d-none');
        coreBlank = false
    }

    if(lowerLevel.value == ''){
        lowerAlert.classList.remove('d-none');
        lowerBlank = true
    }else {
        lowerAlert.classList.add('d-none');
        lowerBlank = false
    }

    return !upperBlank && !coreBlank && !lowerBlank;
}

usernameValidation = function () {
    const username = document.querySelector('[name=username]');
    const usernameAlert = document.querySelector('[name=usernameAlert]');

    if(username.value == ''){
        usernameAlert.innerText = '아이디를 입력해주세요.';
        usernameAlert.classList.remove('d-none');
        return false
    }else {
        usernameAlert.classList.add('d-none');
        return true
    }
}

emailValidation = function () {
    const email = document.querySelector('[name=email]');
    const emailAlert = document.querySelector('[name=emailAlert]');

    if(email.value == ''){
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

levelValidation = function () {
    const level = document.querySelector('[name=level]:checked');
    const levelAlert = document.querySelector('[name=levelAlert]')

    if(level == null){
        levelAlert.classList.remove('d-none');
        return false;
    }else {
        levelAlert.classList.add('d-none');
        return true;
    }
}

signup = function () {
    if(validate()) {
        if(emailAvailable && passwordAvailable) {
            const account = {
                username: document.querySelector('[name=username]').value,
                email: document.querySelector('[name=email]').value,
                password: document.querySelector('[name=password]').value,
                nickname: document.querySelector('[name=nickname]').value,
                gender: document.querySelector('[name=gender]').value == 'M' ? "M" : "F",
                age: document.querySelector('[name=age]').value,
                level: document.querySelector('[name=level]:checked').value,
                upperLevel: document.querySelector('[name=upperLevel]').value,
                coreLevel: document.querySelector('[name=coreLevel]').value,
                lowerLevel: document.querySelector('[name=lowerLevel]').value,
                height: document.querySelector('[name=height]').value,
                weight: document.querySelector('[name=weight]').value,
            };
            fetch("/signup/execute", {
                method: 'post',
                headers: {
                    'Content-Type': 'application/json'
                },
                body: JSON.stringify(account)
            }).then(res => {
                console.log(res);
                if (res.status === 200) {
                    alert("가입되었습니다.")
                    document.location.href = '/';
                    console.log("success")
                } else {
                    console.log("failure")
                }
            })
        } else {
            alert("아이디와 패스워드를 확인해주세요.")
        }
    } else {
        alert("필수 항목값을 입력해주세요")
    }
}
