import { View, Text, Button, ImageBackground, TextInput, SafeAreaView, Alert, TouchableOpacity } from 'react-native';
import { useRouter } from 'expo-router';
import React, { useState } from 'react';
import Checkbox from 'expo-checkbox';
import Toast from "react-native-toast-message";
import { createUser } from "../../services/api";

const SignupForm = () => {
    const router = useRouter();

    // 회원가입 변수 저장
    const [email, setEmail] = useState('');
    const [name, setName] = useState('');
    const [relation, setRelation] = useState('');
    const [isChecked, setChecked] = useState(false);

    // 회원가입 조건 체크
    const [idCheck, setIdCheck] = useState(false);
    const [nameCheck, setNameCheck] = useState(false);
    const [relationCheck, setRelationCheck] = useState(false);
    const [boxCheck, setBoxCheck] = useState(false);

    // 이메일 인증 버튼 (클릭시 비활성화)
    const [disabled, setDisabled] = useState(false);

  // 이메일 인증 반영 
  // axios
  // ★ 나중에 등록 요청 보내는 내용 추가하기
  const emailCheck = async () => {
    try {

      const JsonData = {
        email : email,
      }
      
      const response = await createUser(JsonData);
      console.log(response.data)
      console.log("### success? ###")

      Toast.show({
        type: 'success',
        text1: '이메일 인증에 성공했습니다',
        text2: ':happy:'
      })

      setIdCheck(true)
      setDisabled(true)
    }
    catch (error) {

      console.log(error)

      Toast.show({
        type: 'error',
        text1: '이메일 인증에 실패했습니다.',
        text2: ':cry:'
      })
    }
    }

    // 피보호자 관계 선택 반영
    const wardCheck = () => {
      setRelation('피보호자')
      setRelationCheck(true)
    }

    // 보호자 관계 선택 반영
    const familyCheck = () => {
      setRelation('보호자')
      setRelationCheck(true)
    }

    // 개인정보 동의 체크 반영
    const consentCheck = () => {
      setChecked(!isChecked)
      setBoxCheck(!boxCheck)
    }

    // 이름 입력 반영: 두 글자 이상일 때만
    const nameConfirm = (value:string) => {
      if (value.length >= 2) {
        setName(value)
        setNameCheck(true)
      }
      else{
        setNameCheck(false)
      }
    }

    // 테스트
    const checkPrint = () => {
      console.log(idCheck)
      console.log(nameCheck)
      console.log(relationCheck)
      console.log(boxCheck)
      console.log(name)

    }

    // 회원가입 검증
    // ★ 나중에 등록 요청 보내는 내용 추가하기
    const signupCheck = () => {
      if (idCheck && nameCheck && relationCheck && boxCheck) {
        Alert.alert('회원 정보가 등록되었습니다')
        router.push('signup/page2')
      }
      else {
        Alert.alert('입력 정보를 다시 확인해주세요')
      }
    }
    

    return (
    <View className='flex-1 justify-center items-center'>
      <Text className='text-xl my-2'>회원가입 양식</Text>
      <SafeAreaView>
        <Text className='my-2'>이메일 아이디</Text>
        <View className='flex-row justify-between w-64'>
          <TextInput className='bg-white w-44 rounded-lg px-2' onChangeText={(email) => setEmail(email)}></TextInput>
          <TouchableOpacity 
            className={`${!disabled ? 'bg-sky-500' : 'bg-gray-500'} w-16 h-8 rounded justify-center`}
            // className='w-16 h-8 rounded justify-center bg-sky-500'
            onPress={emailCheck} disabled={disabled}>
          <Text className='text-white text-center font-bold'>인증하기</Text></TouchableOpacity>
        </View>
        <Text className='my-2'>이름</Text>
          <TextInput className='bg-white w-64 rounded-lg px-2' onChangeText={(name) => nameConfirm(name)}></TextInput>
        <Text className='my-2'>관계</Text>
        <View className='flex-row justify-between w-64'>
          <TouchableOpacity className={`${relation=='피보호자' ? 'bg-sky-500' : 'bg-white'} w-28 h-8 rounded-3xl justify-center`} 
            onPress={wardCheck}>
            <Text className={`${relation=='피보호자' ? 'text-white' : 'text-black'} text-center rounded-3xl`}>피보호자</Text></TouchableOpacity>
          <TouchableOpacity className={`${relation=='보호자' ? 'bg-sky-500' : 'bg-white'} w-28 h-8 rounded-3xl justify-center`} 
            onPress={familyCheck}>
            <Text className={`${relation=='보호자' ? 'text-white': 'text-black'} text-center rounded-3xl`}>보호자</Text></TouchableOpacity>    
        </View>
        <View className='flex-row justify-between w-64 my-5'>
          <Checkbox value={isChecked} onValueChange={consentCheck} className={`${isChecked ? '' : 'bg-white border-transparent'}`} />
          <Text>개인정보 수집 및 이용에 동의합니다.</Text>
        </View>
      </SafeAreaView>
      <Button 
        title="다음" 
        onPress={signupCheck}></Button>
    </View>
    );
}

export default SignupForm;