package com.swig.zigzzang.member;

import com.swig.zigzzang.MemberHospital.MemberHospital;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
@Getter
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_id")
    private Long memberId;

    @Column
    private String nickname;

    @Column
    private String userId;

    @Column
    private String password;

    @Column
    private String email;

    @OneToMany(mappedBy = "member")
    private List<MemberHospital> memberHospitals = new ArrayList<>();

    // 추가 코드 생략
}