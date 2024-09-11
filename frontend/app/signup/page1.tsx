import { View, Text, Button, ImageBackground, TextInput, SafeAreaView, Alert, TouchableOpacity } from 'react-native';
import { useRouter } from 'expo-router';
import React, { useState } from 'react';

export default function page1(){
  const router = useRouter();

  // 회원가입 변수 저장
  const [id, setId] = useState('');
  const [name, setName] = useState('');
  const [relation, setRelation] = useState('');


  return (
    <ImageBackground source={require('../../src/assets/POBbackGround.png')} style={{ flex: 1 }}>
    <View style={{ flex: 1, justifyContent: 'center', alignItems: 'center' }}>
      <Text style={{fontSize:20}}>회원가입 양식</Text>
      <SafeAreaView>
      <View style={{height:10}}></View>
      <Text>아이디</Text>
      <View style={{height:5}}></View>
      <TextInput style={{backgroundColor:'white', width:250,}} 
        onChangeText={(id) => setId(id)}>
      </TextInput>
      <View style={{height:5}}></View>
      <View style={{height:10}}></View>
      <Text>이름</Text>
      <View style={{height:5}}></View>
      <TextInput style={{backgroundColor:'white', width:250}} 
        onChangeText={(name) => setName(name)}>
      </TextInput>
      <View style={{height:10}}></View>
      <Text>관계</Text>
      <View style={{height:5}}></View>
      <View style={{flexDirection:'row', justifyContent:'space-between', width:250}}>
        <TouchableOpacity style={{width:120, borderRadius:40, backgroundColor:'white'}}><Text>부모</Text></TouchableOpacity>
        <View style={{width:120, borderRadius:20}}><Button title="자녀" onPress={() => setRelation('자녀')}></Button></View>
      </View>
      <View style={{height:10}}></View>
      <Text>개인정보 수집 및 이용에 동의합니다.</Text>
      <View style={{height:30}}></View>
      </SafeAreaView>

      <Button title="다음" onPress={() => router.push('signup/page2')}></Button>
    </View>
    </ImageBackground>

  );
}
