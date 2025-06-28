import { Pressable, Text } from "react-native";

export const SetupButton = ({
  label,
  onPress,
  image,
  disabled = false,
  isLoading = false,
}: {
  label: string;
  onPress: () => any;
  image?: React.ReactNode;
  isLoading?: boolean;
  disabled?: boolean;
}) => {
  return (
    <Pressable
      style={{
        backgroundColor: disabled ? "#ccc" : "#007AFF",
        padding: 16,
        borderRadius: 8,
        flexDirection: "row",
        alignItems: "center",
        justifyContent: "center",
        width: "100%",
        opacity: isLoading ? 0.5 : 1,
      }}
    >
      {image && <>{image}</>}
      {label && <Text>{label}</Text>}
    </Pressable>
  );
};
