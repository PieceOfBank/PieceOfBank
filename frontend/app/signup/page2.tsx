import { View, Text, Button, ImageBackground, Alert, TextInput } from 'react-native';
import { useRouter } from 'expo-router';
import React, { useState } from 'react';

export default function page1(){
  const router = useRouter();
  const [abc, setAbc] = useState(1);
  const [pinNum, setPinNum] = useState('');
  const [pinNumCheck, setPinNumCheck] = useState('');

  if (abc == 1){
    return (
      <ImageBackground source={require('../../src/assets/POBbackGround.png')} style={{ flex: 1 }}>
      <View style={{ flex: 1, justifyContent: 'center', alignItems: 'center' }}>
        <Text style={{ fontSize:30 }}>비밀번호 설정</Text>
        <Text style={{ fontSize:20 }}>6자리를 입력해주세요</Text>
        <TextInput style={{backgroundColor:'white', width:250,}}
          keyboardType='numeric' 
          onChangeText={(pinNum) => setPinNum(pinNum)}
          >
        </TextInput>
        <Button title="확인" onPress={() => setAbc(2)}></Button>
      </View>
      </ImageBackground>
    );
  } else if (abc == 2){
    return (
      <ImageBackground source={require('../../src/assets/POBbackGround.png')} style={{ flex: 1 }}>
      <View style={{ flex: 1, justifyContent: 'center', alignItems: 'center' }}>
        <Text style={{ fontSize:30 }}>비밀번호를 설정</Text>
        <Text style={{ fontSize:20 }}>확인을 위해 다시 입력해주세요</Text>
        <TextInput style={{backgroundColor:'white', width:250,}}
          keyboardType='numeric' 
          onChangeText={(pinNumCheck) => setPinNumCheck(pinNumCheck)}
          >
        </TextInput>

        <Button title="다음" onPress={() => 
          Alert.alert('확인', '확인되었습니다',[
            {
              text:"다음",
              onPress:() => router.push("signup/page3")
            }
          ])
        }></Button>
      </View>
      </ImageBackground>
    );
  }

}
