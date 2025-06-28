import { ActivityWithTitle } from "@/components/Screen";
import { SetupButton } from "@/components/ui/SetupButton";
import React from "react";
import { Text, View } from "react-native";

export const SetupScreen = () => {
  return (
    <ActivityWithTitle title={"Welcome to CC"}>
      <View
        style={{
          flexGrow: 1,
          display: "flex",
          flexDirection: "column",
          //   justifyContent: "space-between",
        }}
      >
        <View>
          <Text>
            You&apos;ll need to give permission for CC to access Notification
            read, reply and control in Android settings.
          </Text>
          <Text>
            CC uses your notifications to detect conversations and know what
            messages you haven&apos;t yet seen.
          </Text>
        </View>

        <SetupButton onPress={() => {}} label={"Grant access"} />
      </View>
    </ActivityWithTitle>
  );
};
