let usernameDupl = true;
let passwordDupl = true;
window.onload = function () {
    usernameCheck();
    passwordCheck();
};

usernameCheck = function () {
    const username = document.querySelector('[name=username]');
    const usernameAlert = document.querySelector('[name=usernameAlert]');
    const usernameSuccess = document.querySelector('[name=usernameSuccess]');

    username.addEventListener('blur', function () {
        fetch("/signup/check", {
            method : 'post',
            headers : {
                'Content-Type' : 'application/json'
            },
            body: username.value
        }).then(res => {
            if(res.status === 200){
                usernameAlert.classList.add('d-none');
                usernameSuccess.classList.remove('d-none');
                usernameDupl = true;
            }else if (res.status === 409){
                usernameAlert.classList.remove('d-none');
                usernameSuccess.classList.add('d-none');
                usernameDupl = false;
            }else {
                console.log(res);
            }
        })
    })
};

passwordCheck = function () {
    const password1 = document.querySelector('[name=password]');
    const password2 = document.querySelector('[name=password2]');
    const passwordAlert = document.querySelector('[name=passwordAlert]');

    password1.addEventListener('keyup', function () {
        if (password1.value !== password2.value && password2.value !== '') {
            passwordAlert.classList.remove('d-none');
            passwordDupl = false;
        } else {
            passwordAlert.classList.add('d-none');
            passwordDupl = true;
        }
    });

    password2.addEventListener('keyup', function () {
        if(password1.value !== password2.value){
            passwordAlert.classList.remove('d-none');
            passwordDupl = false;
        }else{
            passwordAlert.classList.add('d-none');
            passwordDupl = true;
        }
    })
};

signup = function () {
    if(usernameDupl && passwordDupl){

        const account = {
            username : document.querySelector('[name=username]').value,
            password : document.querySelector('[name=password]').value,
            nickname : document.querySelector('[name=nickname]').value,
            gender : document.querySelector('[name=gender]').value,
            age: document.querySelector('[name=age]').value
        };
        fetch("/signup/execute", {
            method : 'post',
            headers : {
                'Content-Type' : 'application/json'
            },
            body: JSON.stringify(account)
        }).then(res => {
            console.log(res);
            if(res.status === 200){
                alert("가입되었습니다.")
                document.location.href = '/';
                console.log("success")
            }else{
                console.log("failure")
            }
        })
    } else {
        alert("아이디와 패스워드를 확인해주세요.")
    }
}
