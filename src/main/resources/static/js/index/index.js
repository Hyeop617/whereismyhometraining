login = function () {
    const username = document.querySelector('[name=username]').value;
    const password = document.querySelector('[name=password]').value;
    if(username != '' && password != ''){
        const user = {
            username: username,
            password: password
        };
        console.log(user);
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
                document.location.href = '/'
            }
        }).catch(err =>
          console.log(err)
        )
    } else {
        alert("아이디와 비밀번호를 입력하세요.")
    }

};