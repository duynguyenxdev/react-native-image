import codegenNativeComponent from 'react-native/Libraries/Utilities/codegenNativeComponent';
import type { ViewProps } from 'react-native';

export interface SvgViewNativeProps extends ViewProps {
  xml?: string;
  url?: string;
  resizeMode?: string;
}

export default codegenNativeComponent<SvgViewNativeProps>('SvgView');
