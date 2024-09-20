import { View, Text, Button, ImageBackground, Alert } from 'react-native';
import { useRouter } from 'expo-router';
import React, { useState } from 'react';
import Checkbox from 'expo-checkbox';

export default function page1(){
  const router = useRouter();

  // 계좌번호 페이지 확인 (1 - 1차, 2 - 2차)
  const [def, setDef] = useState(1);

  const [accountChecked, setAccountChecked] = useState(false);

  if (def == 1){
    return (
      <ImageBackground source={require('../../src/assets/POBbackGround.png')} style={{ flex: 1 }}>
      <View className='flex-1 justify-center items-center'>
        <Text className='text-3xl my-1'>계좌등록</Text>
        <Text className='text-2xl my-1'>서비스를 이용할 계좌를 선택해주세요</Text>
        <View className='w-80 h-10 p-2 flex-row bg-white'>
          <Checkbox value={accountChecked} onValueChange={setAccountChecked}></Checkbox>
          <View className='w-8'></View>
          <Text>국민은행</Text>
          <Text>123-456-7890</Text>
        </View>
        <Button title="확인" onPress={() => setDef(2)}></Button>
      </View>
      </ImageBackground>
    );
  } else if (def == 2){
    return (
      <ImageBackground source={require('../../src/assets/POBbackGround.png')} style={{ flex: 1 }}>
      <View className='flex-1 justify-center items-center'>
        <Text className='text-3xl'>계좌등록 하시겠습니까?</Text>
        <Button title="다음" onPress={() => 
          Alert.alert('확인', '확인되었습니다',[
            {
              text:"서비스 이용하기",
              onPress:() => router.push("/")
            }
          ])
          }></Button>
      </View>
      </ImageBackground>
    );
  }
}
