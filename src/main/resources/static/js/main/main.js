login = function () {
    const user = {
        username: document.querySelector('[name=username]').value,
        password: document.querySelector('[name=password]').value
    };
    fetch("/login", {
        method: "post",
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(user)
        // TODO :: promise 리턴이 이상함....
    }).then(res => {
        let promise = res.text();
        if (res.status === 401) {
            alert("아이디 혹은 비밀번호를 확인해주세요.")
        } else if (res.status === 200){
            document.location.href = '/training'
        }
    }).catch(err =>
        console.log(err)
    )
};