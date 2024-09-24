import { View, Text, Button, ImageBackground, Alert, TextInput, TouchableOpacity, ScrollView, StyleSheet } from 'react-native';
import { useRouter, Link } from 'expo-router';
import React, { useState } from 'react';

const TransactionList = () => {
    interface TransferItem {
        id: string;
        name: string;
        type: string;
    }
     // 임시 리스트 (거래)
     const transferList: TransferItem [] = [{id:'1', type:'1', name:'9월 1일'}, {id:'2', type:'1', name:'9월 2일'}, {id:'3', type:'2', name:'9월 3일'}, {id:'4', type:'1',name:'9월 4일'}, {id:'5', type:'2',name:'9월 5일'}]

    
     return(
        <View className='bg-green-300 flex-1'>
            <ScrollView className='w-96 flex-1'>
            {transferList.map((list, index) => (
                <View key={index} className={`${list.type == '1' ? 'items-start' : 'items-end'} flex-1 justify-center m-2`}>
                    <Text className={`${list.type == '1' ? 'bg-white' : 'bg-yellow-300'} flex-row w-60 h-32 my-2 p-2 rounded-xl `}>{list.name}</Text>
                </View>
                ))}
            </ScrollView>
        </View>
    )



}

export default TransactionList
