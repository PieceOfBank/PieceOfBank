import { View, Text, SafeAreaView, TextInput, Button, TouchableOpacity } from "react-native";
import { Link } from "expo-router";
import { useState } from "react";
import Toast from 'react-native-toast-message';
import Header from "../../src/ui/components/Header";
import CancelButton from "../../src/ui/components/CancelButton";
import { useRouter} from 'expo-router';


const AddWard = () => {
  const [wardId, setWardId] = useState<string>("");
  const [relation, setRelation] = useState<string>("");

  const router = useRouter()

  /* 알림팝업 Logic */
  const handleBtn = () => {
    Toast.show({
      type: 'success',
      text1: '피보호자 등록 요청 완료',
      text2: '피보호자의 수락 후 메인 서비스를 이용하실 수 있습니다! 👋'
    })
    router.push('/family copy/familyMain')
  }

  return (
    <View className='flex-1'>
      <Header />
        <View className='justfiy-center items-center'> 
        <Text className="w-60 p-2 bg-gray-300 rounded-3xl text-center justfiy-center items-center m-5 text-lg">피보호자 등록 신청</Text>
        <View className='border bg-white border-gray-300 rounded-2xl p-5 mt-10 h-60'>
          <SafeAreaView>
            <Text className="mt-6 mb-2 pl-2">피보호자 아이디</Text>
            <TextInput className="mt-1 mb-10 w-56 bg-white border border-gray-200 rounded-3xl" onChangeText={(id) => setWardId(id)}></TextInput>
          </SafeAreaView>
          <View className='flex-row justify-center items-center'>
            <TouchableOpacity className='m-2 py-2 px-4 bg-sky-400 rounded-3xl' onPress={handleBtn}>
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
