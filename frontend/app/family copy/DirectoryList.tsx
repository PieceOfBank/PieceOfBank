import { View, Text, SafeAreaView, TextInput, Button, TouchableOpacity } from "react-native";
import { Link } from "expo-router";
import { useState } from "react";
import { useRouter } from 'expo-router';
import Toast from 'react-native-toast-message';
import Header from "../../src/ui/components/Header";
import CancelButton from "../../src/ui/components/CancelButton";
import ListInput from "../../src/ui/components/Temporary/ListInput";

const DirectoryList = () => {
    const [oneLimit, setOneLimit] = useState<string>("");
    const [todayLimit, setTodayLimit] = useState<string>("");
    const router = useRouter()
  /* 알림팝업 Logic */
  const handleBtn = () => {
    Toast.show({
      type: 'success',
      text1: 'Hello',
      text2: 'This is some something 👋'
    })
  }

  return (
    <View className='flex-1'>
      <Header />
      <View className='flex-1'>
        <View className='justify-center items-center bg-gray-300 rounded-3xl mt-12 p-3 mx-12 mb-5'>
          <Text className='text-xl text-center font-semibold mb-2'>부모 송금 대상 관리</Text>
          <Text className='text-center text-gray-500'>최대 5명까지 설정 가능합니다</Text>
        </View>
        <ListInput />

      </View>

    </View>
  );
};

export default DirectoryList;