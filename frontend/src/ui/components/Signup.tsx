import { View, Text, Button, ImageBackground, TextInput, SafeAreaView, Alert, TouchableOpacity } from 'react-native';
import { useRouter } from 'expo-router';
import React, { useState } from 'react';
import Checkbox from 'expo-checkbox';

const SignupForm = () => {
    const router = useRouter();

    // 회원가입 변수 저장
    const [id, setId] = useState('');
    const [name, setName] = useState('');
    const [relation, setRelation] = useState('');
    const [isChecked, setChecked] = useState(false);

    // 회원가입 조건 체크
    const [idCheck, setIdCheck] = useState(false);
    const [nameCheck, setNameCheck] = useState(false);
    const [relationCheck, setRelationCheck] = useState(false);
    const [boxCheck, setBoxCheck] = useState(false);

    const [disabled, setDisabled] = useState(false);

    // 이메일 인증
    const emailCheck = () => {
        Alert.alert('이메일 등록이 완료되었습니다')
        setIdCheck(true)
        setDisabled(true)
    }
      
    return (
      <View className='flex-1 justify-center items-center'>
      <Text className='text-xl my-2'>회원가입 양식</Text>
      <SafeAreaView>
        <Text className='my-2'>이메일 아이디</Text>
        <View className='flex-row justify-between w-64'>
          <TextInput className='bg-white w-44 rounded-lg px-2' onChangeText={(id) => setId(id)}></TextInput>
          <TouchableOpacity className='w-16 h-8 rounded justify-center bg-sky-500'
            onPress={emailCheck} disabled={disabled}>
          <Text className='text-white text-center font-bold'>인증하기</Text></TouchableOpacity>
        </View>
        <Text className='my-2'>이름</Text>
        <TextInput className='bg-white w-64 rounded-lg px-2' onChangeText={(name) => setName(name)}></TextInput>
        <Text className='my-2'>관계</Text>
        <View className='flex-row justify-between w-64'>
          <TouchableOpacity className={`${relation=='부모' ? 'bg-sky-500' : 'bg-white'} w-28 h-8 rounded-3xl justify-center`} onPress={() => setRelation('부모')}>
            <Text className={`${relation=='부모' ? 'text-white' : 'text-black'} text-center rounded-3xl`}>부모</Text></TouchableOpacity>
          <TouchableOpacity className={`${relation=='자녀' ? 'bg-sky-500' : 'bg-white'} w-28 h-8 rounded-3xl justify-center`} onPress={() => setRelation('자녀')}>
            <Text className={`${relation=='자녀' ? 'text-white': 'text-black'} text-center rounded-3xl`}>자녀</Text></TouchableOpacity>    
        </View>
        <View className='flex-row justify-between w-64 my-5'>
          <Checkbox value={isChecked} onValueChange={setChecked} className={`${isChecked ? '' : 'bg-white border-transparent'}`} />
          <Text>개인정보 수집 및 이용에 동의합니다.</Text>
        </View>
      </SafeAreaView>
      <Button title="다음" onPress={() => router.push('signup/page2')}></Button>
    </View>
    );
}

export default SignupForm;