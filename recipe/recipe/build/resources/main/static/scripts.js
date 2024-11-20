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

    // 현재 로그인된 사용자 정보 가져오기
    fetch('/api/auth/currentUser', {
        method: 'GET',
        credentials: 'same-origin' // 동일한 출처로 쿠키를 포함한 요청
    })
        .then(response => {
            if (response.status === 200) {
                return response.json();  // 로그인된 사용자 정보 반환
            } else {
                throw new Error('로그인된 사용자가 없습니다.');
            }
        })
        .then(data => {
            console.log('현재 로그인된 사용자:', data);  // 사용자 정보 출력
            const userId = data.userId;
            document.getElementById('userIdDisplay').innerText = userId;
        })
        .catch(error => {
            console.log(error);  // 에러 출력
        });


    // 로그아웃 요청 처리 (로그아웃 버튼 클릭 시)
    document.getElementById('loginLink').addEventListener('click', function(event) {
        if (event.target.textContent === '로그아웃') {
            fetch('/api/auth/logout', {
                method: 'POST',
                credentials: 'same-origin' // 동일한 출처로 쿠키를 포함한 요청
            })
                .then(() => {
                    location.reload(); // 로그아웃 후 페이지 새로고침
                })
                .catch(error => console.log('로그아웃 실패:', error));
        }
    });

    // 로그인 상태를 확인하는 fetch 요청
    fetch('/api/auth/currentUser', {
        method: 'GET',
        credentials: 'same-origin' // 동일한 출처로 쿠키를 포함한 요청
    })
        .then(response => {
            if (response.status === 200) {
                return response.json(); // 로그인된 사용자 정보 반환
            } else {
                throw new Error('로그인된 사용자가 없습니다.');
            }
        })
        .then(data => {
            // console.log('현재 로그인된 사용자:', data); // 사용자 정보 출력 - 위에 있어서 두 번 출력됨
            // 로그인된 경우 메뉴를 업데이트
            document.getElementById('loginLink').textContent = '로그아웃';
            document.getElementById('loginLink').setAttribute('href', '/api/auth/logout');
            document.getElementById('registerLink').textContent = '마이페이지';
            document.getElementById('registerLink').setAttribute('href', '/pages/mypage');
        })
        .catch(error => {
            console.log(error); // 에러 출력
        });



    /************************
    * 여기서부터 회원정보 수정 로직
    * ***********************/

    // 프로필 사진 변경 처리
    document.getElementById('profile-pic-input').addEventListener('change', function(event) {
        const file = event.target.files[0];

        if (file) {
            // FormData를 사용하여 파일을 서버에 전송
            const formData = new FormData();
            formData.append('profileImage', file);  // 프로필 이미지 파일 추가

            fetch('/api/users/uploadProfileImage', {
                method: 'POST',
                body: formData
            })
                .then(response => response.json())
                .then(data => {
                    if (data.success) {
                        // 이미지 업로드 성공 시, 새 URL로 프로필 사진 업데이트
                        document.querySelector('.profile-pic img').src = data.imageUrl;
                        alert('프로필 사진이 업데이트되었습니다.');
                    } else {
                        alert('프로필 사진 업데이트에 실패했습니다.');
                    }
                })
                .catch(error => {
                    console.error('Error:', error);
                    alert('프로필 사진 업로드 중 오류가 발생했습니다.');
                });
        }
    });

    document.getElementById('updateForm').addEventListener('submit', function(event) {
        event.preventDefault();

        // 사용자 입력 값 가져오기
        const userId = document.getElementById('userIdDisplay').value;
        const nickname = document.getElementById('nickname').value;
        const email = document.getElementById('email').value;
        const phone = document.getElementById('phone').value;
        const currentPassword = document.getElementById('current-password').value;
        const newPassword = document.getElementById('new-password').value;
        const confirmPassword = document.getElementById('confirm-password').value;

        // 비밀번호 불일치 확인
        if (newPassword !== confirmPassword) {
            alert('새 비밀번호와 확인 비밀번호가 일치하지 않습니다.');
            return;
        }

        // 프로필 이미지 URL (변경된 경우 또는 기존 URL)
        const profileImage = document.querySelector('.profile-pic img').src;

        // 수정된 사용자 정보 객체
        const userData = {
            userId: userId,
            nickname: nickname,
            email: email,
            phone: phone,
            password: newPassword,
            profileImage: profileImage || null // profileImage가 없다면 null로 전송
        };

        // 사용자 정보 서버로 전송
        fetch(`/api/users/updateUser`, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(userData)
        })
            .then(response => response.json())
            .then(data => {
                if (data.success) {
                    alert('회원 정보가 성공적으로 수정되었습니다.');
                    window.location.href = '/mypage';  // 수정 후 마이페이지로 이동
                } else {
                    alert('회원 정보 수정 실패');
                }
            })
            .catch(error => {
                console.error('Error:', error);
                alert('회원 정보 수정 중 오류가 발생했습니다.');
            });
    });

});
