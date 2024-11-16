$(document).ready(function () {

    // 아이디 중복 체크
    $('#checkIdBtn').on('click', function () {
        const id = $('#id').val();
        if (id) {
            $.get('/api/auth/check-duplicate-id', {userId: id}, function (response) {
                if (response) {
                    $('#idCheckMessage').text('이미 사용 중인 아이디입니다.').css('color', 'red');
                } else {
                    $('#idCheckMessage').text('사용 가능한 아이디입니다.').css('color', 'green');
                }
            });
        } else {
            alert('아이디를 입력해 주세요.');
        }
    });

    // 이메일 중복 체크
    $('#checkEmailBtn').on('click', function () {
        const email = $('#email').val();
        if (email) {
            $.get('/api/auth/check-duplicate-email', {userEmail: email}, function (response) {
                if (response) {
                    $('#emailCheckMessage').text('이미 사용 중인 이메일입니다.').css('color', 'red');
                } else {
                    $('#emailCheckMessage').text('사용 가능한 이메일입니다.').css('color', 'green');
                }
            });
        } else {
            alert("이메일을 입력해 주세요.");
        }
    });

    // 전화번호 중복 체크
    $('#checkPhoneBtn').on('click', function () {
        const userPhone = $('#phone').val();
        if (userPhone) {
            $.get('/api/auth/check-duplicate-phone', {userPhone: userPhone}, function (response) {
                if (response) {
                    $('#phoneCheckMessage').text('이미 사용 중인 전화번호입니다.').css('color', 'red');
                } else {
                    $('#phoneCheckMessage').text('사용 가능한 전화번호입니다.').css('color', 'green');
                }
            });
        } else {
            alert('전화번호를 입력해 주세요.');
        }
    });

    // 회원가입 폼 제출 처리
    $('#registerForm').on('submit', function (event) {
        event.preventDefault();  // 폼 제출을 막음

        // 비밀번호 불일치 시 메세지 띄우기
        const password = $('#password').val();
        const confirmPW = $('#confirmuserPW').val();
        if (password !== confirmPW) {
            alert('비밀번호가 일치하지 않습니다.');
            return;
        }

        const userData = {
            userId: $('#id').val(),
            password: $('#password').val(),
            userName: $('#nickname').val(),
            email: $('#email').val(),
            phone: $('#phone').val(),
            confirmuserPW: $('#confirmuserPW').val()
        };

        // 데이터 전송
        fetch(`/api/auth/register`, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(userData)
        })
            .then(response => response.text())  // 응답을 text로 받음
            .then(data => {
                if (data === "회원가입 성공! 로그인 화면으로 이동합니다.") {
                    // 회원가입 성공 메시지를 받으면 로그인 페이지로 리다이렉트
                    window.location.href = '/pages/login';
                } else {
                    throw new Error('회원가입 실패');
                }
            })
            .catch(error => {
                console.error('회원가입 실패:', error);
                alert('회원가입 실패');
            });
    });

    // 로그인 상태 체크
    const isLoggedIn = localStorage.getItem('loggedIn') === 'true';
    const loginMenu = $('.login_menu');

    // 로그인 상태에 따른 메뉴 업데이트
    if (isLoggedIn) {
        loginMenu.html(`
            <li><a href="#" onclick="logout()">로그아웃</a></li>
            <li><a href="mypage.html">마이페이지</a></li>`);
    } else {
        loginMenu.html(`
            <li><a href="login.html">로그인</a></li>
            <li><a href="register.html">회원가입</a></li>`);
    }

});
