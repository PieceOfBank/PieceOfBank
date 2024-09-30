import { Link, useNavigation, useRouter, useLocalSearchParams } from "expo-router";
import { View, Text, ImageBackground, TextInput, SafeAreaView } from 'react-native';
import React, { useState, useEffect } from 'react';
import Header from "../../src/ui/components/Header";
import FamilyTransaction from "../../src/ui/components/Temporary/FamilyTransaction";

const DealHistory = () => {

    return(
        <View className='flex-1 justify-center items-center'>
        <Text className='text-xl'>엄마와의 기록</Text>
        <FamilyTransaction />
        <Link className='w-32 p-2 rounded-3xl justify-center items-center font-bold bg-sky-300 m-2 text-center text-2xl text-white' 
          href={{pathname:'/family copy/sendMoney'}}>송금하기</Link>
        <View className='h-40'></View>
      </View>
    )
}

export default DealHistory;