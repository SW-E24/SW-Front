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

    // 수정한 회원 정보 폼 제출 처리
    $('#updateForm').on('submit', function (event) {
        event.preventDefault();  // 폼 제출을 막음

        // 비밀번호 불일치 시 메세지 띄우기
        const password = $('#new-password').val();
        const confirmPassword = $('#confirm-password').val();
        if (password !== confirmPassword) {
            alert('비밀번호가 일치하지 않습니다.');
            return;
        }

        // 현재 로그인된 사용자 아이디 가져오기
        fetch('/api/auth/currentUser', {
            method: 'GET',
            credentials: 'same-origin'
        })
            .then(response => response.json())
            .then(data => {
                if (data) { // 로그인 된 상태
                    // 디버깅용
                    console.log("현재 로그인된 사용쟈:", data)

                    // 현재 사용자 아이디
                    const userId = data.userId;

                    // 수정된 회원 데이터 준비
                    const userData = {
                        password: $('#new-password').val(),
                        userName: $('#nickame').val(),
                        email: $('#email').val(),
                        phone: $('#phone').val(),
                    };

                    // 데이터 전송
                    fetch('/api/users/updateProfile', {
                        method: 'POST',
                        headers: {
                            'Content-Type': 'application/json'
                        },
                        body: JSON.stringify(userData)
                    })
                        .then(response => response.text())  // 응답을 text로 받음
                        .then(data => {
                            if (data === "회원 정보 수정 성공!") {
                                // 성공하면 마이페이지로 리다이렉트
                                window.location.href = '/pages/mypage';
                            } else {
                                throw new Error('회원 정보 수정 실패');
                            }
                        })
                        .catch(error => {
                            console.error('회원 정보 수정 실패:', error);
                            alert('회원 정보 수정 실패');
                        });
                } else {
                    alert("로그인된 사용자가 없습니다.");
                }
            })
            .catch(error => {
                console.error("사용자 정보 가져오기 실패", error);
                alert("사용자 정보 가져오기 실패");
            });
    });

    // 로그인 상태 체크 (서버에서 현재 로그인된 사용자 정보 가져오기)
    fetch('/api/auth/currentUser', {
        method: 'GET',
        credentials: 'same-origin' // 동일한 출처로 쿠키를 포함한 요청
    })
        .then(response => {
            if (response.status === 200) {
                // 로그인된 경우
                return response.json();
            } else {
                throw new Error('로그인된 사용자가 없습니다.');
            }
        })
        .then(data => {
            console.log('현재 로그인된 사용자:', data);
            // 로그인 상태에 따른 메뉴 업데이트
            $('.login_menu').html(`
            <li><a href="#" onclick="logout()">로그아웃</a></li>
            <li><a href="mypage.html">마이페이지</a></li>
        `);
        })
        .catch(error => {
            console.log(error);
            // 로그인되지 않은 경우
            $('.login_menu').html(`
            <li><a href="login.html">로그인</a></li>
            <li><a href="register.html">회원가입</a></li>
        `);
        });

    // 로그인 후 로컬 스토리지에 정보 저장 (클라이언트측에서 사용)
    fetch('/api/auth/login', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/x-www-form-urlencoded',
        },
        body: new URLSearchParams({
            'userId': document.getElementById('id').value,
            'password': document.getElementById('password').value
        })
    })
        .then(response => {
            if (response.ok) {
                return response.json(); // 로그인 성공 후 사용자 정보를 받음
            } else {
                throw new Error('로그인 실패');
            }
        })
        .then(data => {
            localStorage.setItem('loggedIn', 'true'); // 로그인 상태를 로컬 스토리지에 저장
            localStorage.setItem('userId', data.userId); // 사용자 아이디 저장
            window.location.href = '/pages/mypage'; // 마이페이지로 리다이렉트
        })
        .catch(error => {
            console.error(error);
            document.getElementById('error-message').innerText = '아이디 또는 비밀번호가 틀립니다.';
        });


    // 로그아웃 처리 (세션 제거)
    window.logout = function () {
        fetch('/api/auth/logout', {
            method: 'POST',
            credentials: 'same-origin' // 쿠키를 포함하여 요청
        })
            .then(response => response.text())
            .then(data => {
                console.log(data); // 로그아웃 성공 메시지 확인
                window.location.href = '/pages/login'; // 로그아웃 후 로그인 페이지로 이동
            })
            .catch(error => {
                console.error('로그아웃 실패:', error);
            });
    };

});
