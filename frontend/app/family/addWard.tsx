import { View, Text, SafeAreaView, TextInput, Button, TouchableOpacity } from "react-native";
import { Link } from "expo-router";
import { useState } from "react";
import Toast from 'react-native-toast-message';
import CancelButton from "../../src/ui/components/CancelButton";
import { useRouter} from 'expo-router';
import { sendExpoNotification, subscriptionPost, subTargetCheck } from "../../src/services/api";
import AsyncStorage from '@react-native-async-storage/async-storage';

const AddWard = () => {
  const [wardId, setWardId] = useState<string>("");
  const [relation, setRelation] = useState<string>("");

  const router = useRouter()

  /* 알림팝업 Logic */
  const subRequest = async() => {
    try{
      const keyGet = await AsyncStorage.getItem("myKey");
      const myKey = JSON.parse(keyGet!)
    
      const JsonData = {
        senderKey: myKey, 
        receiverId: wardId
      }
      const response = await subscriptionPost(JsonData);
      console.log(response.data)

      // 부모 디바이스 토큰 얻기
      // 1. 부모 유저키 얻기
      const subData = await subTargetCheck();
      const protectUserKey = subData.data.protectUser.userKey;
      const notificationMsg = {
        token: protectUserKey,
        title: "구독 신청 알림",
        body: "안전한 금융 거래를 보장할 수 있어요!"
      }
      await sendExpoNotification(notificationMsg);

      Toast.show({
        type: 'success',
        text1: '피보호자 등록 요청 완료',
        text2: '피보호자의 수락 후 메인 서비스를 이용하실 수 있습니다! 👋'
      })
      router.push('/family/familyMain')
    }catch(error){
      console.log(error)
      Toast.show({
        type: 'error',
        text1: '피보호자 등록 요청 실패',
        text2: '입력한 아이디를 다시 확인해주세요'
      })
    }

  }

  return (
    <View className='flex-1'>
        <View className='justfiy-center items-center'> 
        <Text className="w-60 p-2 bg-gray-300 rounded-3xl text-center justfiy-center items-center m-5 text-lg">피보호자 등록 신청</Text>
        <View className='border bg-white border-gray-300 rounded-2xl p-5 mt-10 h-60'>
          <SafeAreaView>
            <Text className="mt-6 mb-2 pl-2">피보호자 아이디</Text>
            <TextInput className="my-3 pl-3 mb-10 w-56 bg-white border border-gray-200 rounded-3xl" onChangeText={(id) => setWardId(id)}></TextInput>
          </SafeAreaView>
          <View className='flex-row justify-center items-center'>
            <TouchableOpacity className='m-2 py-2 px-4 bg-sky-400 rounded-3xl' onPress={subRequest}>
              <Text className='text-white'>요청 보내기</Text>
            </TouchableOpacity>
            <CancelButton />
          </View>

        </View>
      </View>

    </View>
  );
};

export default AddWard;
