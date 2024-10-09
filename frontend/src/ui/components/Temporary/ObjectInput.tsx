import { View, Text, Button, ImageBackground, Alert, TextInput, TouchableOpacity, ScrollView, StyleSheet, SafeAreaView, KeyboardAvoidingView } from 'react-native';
import { useRouter, Link, useLocalSearchParams } from 'expo-router';
import React, { useState } from 'react';
import CancelButton from '../CancelButton';
import { createDirectory } from '../../../services/api';
import * as FileSystem from 'expo-file-system';
import * as ImagePicker from 'expo-image-picker';


interface existsInfo {
    onChange: (inputBalance:string) => void;
    name?: string
}

const ObjectInput = () => { 

    const [name, setName] = useState('')
    const [phone, setPhone] = useState('')
    const [bank, setBank] = useState('')
    const [account, setAccount] = useState('')
    const [imageUri, setImageUri] = useState<string | null>(null);

    const router = useRouter()
    const myUserKey = useSelector((state: RootState) => state.getUserKey.userKey);

     /* 이미지 선택 */
  const selectImage = async () => {
    
    const { status } = await ImagePicker.requestMediaLibraryPermissionsAsync(); // 접근 권한 요청 알림
  
    // 권한 없을 경우 종료시킴
    if (status !== 'granted') {
      console.log('미디어 권한이 부여되지 않았습니다.');
      return;
    } 
  
    // 이미지 선택기 열기
    let result = await ImagePicker.launchImageLibraryAsync({
      mediaTypes: ImagePicker.MediaTypeOptions.Images, // 선택 유형 이미지로 제한
      allowsEditing: true, // 이미지 편집 허용
      aspect: [4, 3], // 편집할 이미지 비율 설정
      quality: 1, // 편집할 이미지 품질 설정
    });
  
    // 이미지 선택 완료하면 IamgeUri에 저장됨
    if (!result.canceled) {
      setImageUri(result.assets[0].uri);
    } 
  };

    const directoryGo = async() => {
      try{
        // 나중에 userkey 어떻게 받아와서 넣어야 할까요? authorization 으로 되는지 확인하기
        
        // interface UserInfo {
        //   userKey: string,
        //   accountNo: string,
        //   institutionCode: number,
        //   name: string,
        //   url: string
        // }
        
        // interface UserDirectory {
        //   directory: UserInfo;
        //   file: string;
        // }

        const userInfo = {
          userKey: "234532543",
          accountNo: "0016893582615978",
          institutionCode: 0,
          name: "test",
          url: "test"
        }

        // const imageInfo = {

        // }
        // const fileInput = document.querySelector('#fileInput'); 
        // const formData = new FormData();
        // formData.append('directory', new Blob([JSON.stringify(directoryData)], { type: "application/json" }))

      //   formData.append('file', 
      //     {
      //     uri: image,
      //     type: 'image/png',
      //     name: 'image.png',
      // } 
      // as any)

      const formData = new FormData();
        
        // FormData에 이미지 추가
        formData.append('file', {
            uri: imageUri, // 선택된 이미지 URI
            type: 'image/jpeg', // MIME 타입 (상황에 따라 조정 가능)
            name: 'image.jpg', // 파일 이름
        } as any);

      // const formData = new FormData();
        const JsonData = {
          directory: {
            accountNo: "0019312432084644",
            institutionCode: 100,
            name: "qkqkqk",
          }
        }

      //   formData.append('directory', JSON.stringify(JsonData.directory));
      //   formData.append('file', {
      //     uri: image.uri,
      //     type: 'image.type',
      //     name: 'image.fileName',
      // });
        console.log(JsonData)
        const response = await createDirectory(JsonData);
        console.log(response)
        } catch(error){
            console.log(error)
        }
    }
    
     return(
        <View className='justify-center items-center p-4'>
          <Text className='mt-12 mb-8 bg-gray-300 px-3 py-2 w-48 rounded-3xl text-xl text-center font-semibold'>대상자 설정</Text>
        <SafeAreaView className='bg-gray-200 rounded-3xl px-6 py-4'>
          <Text className="my-2 ml-2 font-semibold">이름</Text>
          <View className='flex-row'>
            <TextInput className="my-1 w-56 bg-white rounded-3xl py-1" onChangeText={(name) => setName(name)}></TextInput>
          </View>

          <Text className="my-2 ml-2 font-semibold">연락처</Text>
          <View className='flex-row'>
          <TextInput
            className="my-1 w-56 bg-white rounded-3xl py-1"
            keyboardType='numeric'
            onChangeText={(phone) => setPhone(phone)}></TextInput>
          </View>

          <Text className="my-2 ml-2 font-semibold">은행</Text>
          <View className='flex-row'>
          <TextInput
            className="my-1 w-56 bg-white rounded-3xl py-1"
            onChangeText={(bank) => setBank(bank)}></TextInput>
          </View>

          <Text className="my-2 ml-2 font-semibold">계좌번호</Text>
          <View className='flex-row'>
          <TextInput
            className="my-1 w-56 bg-white rounded-3xl py-1"
            keyboardType='numeric'
            onChangeText={(account) => setAccount(account)}></TextInput>
          </View>

        </SafeAreaView>
        <View className='flex-row mt-2'>
          <TouchableOpacity 
            className='m-2 py-2 px-4 bg-red-400 rounded-3xl bg-sky-500'
            onPress={directoryGo} 
            >
            <Text className='text-white text-center font-bold'>설정 완료</Text></TouchableOpacity>
            <CancelButton />
        </View>

      </View>

    )



}

export default ObjectInput
