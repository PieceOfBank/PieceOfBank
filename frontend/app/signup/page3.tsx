import { View, Text, Button, ImageBackground, Alert } from 'react-native';
import { useRouter } from 'expo-router';
import React, { useState } from 'react';

export default function page1(){
  const router = useRouter();
  const [def, setDef] = useState(1);
  if (def == 1){
    return (
      <ImageBackground source={require('../../src/assets/POBbackGround.png')} style={{ flex: 1 }}>
      <View style={{ flex: 1, justifyContent: 'center', alignItems: 'center' }}>
        <Text style={{ fontSize:30 }}>계좌등록</Text>
        <Text style={{ fontSize:20 }}>서비스를 이용할 계좌를 선택해주세요</Text>
        <Button title="확인" onPress={() => setDef(2)}></Button>
      </View>
      </ImageBackground>
    );
  } else if (def == 2){
    return (
      <ImageBackground source={require('../../src/assets/POBbackGround.png')} style={{ flex: 1 }}>
      <View style={{ flex: 1, justifyContent: 'center', alignItems: 'center' }}>
        <Text style={{ fontSize:30 }}>계좌등록 하시겠습니까?</Text>
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
