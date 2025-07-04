import { SvgView } from '@dn/react-native-image';
import { StyleSheet } from 'react-native';

const url = 'https://files.svgcdn.io/streamline-emojis/chicken.svg';

const SampleSvgUrl = () => {
  return <SvgView url={url} style={styles.svgView} resizeMode="cover" />;
};

export default SampleSvgUrl;

const styles = StyleSheet.create({
  svgView: {
    width: 100,
    height: 100,
  },
});
